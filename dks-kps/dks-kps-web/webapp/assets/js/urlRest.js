var Nibbler = function (options) {
    var construct, pad, dataBits, codeBits, keyString, arrayData, mask, group, max, gcd, translate, encode, decode, utf16to8, utf8to16;
    construct = function () {
        var i, mag, prev;
        pad = options.pad || '';
        dataBits = options.dataBits;
        codeBits = options.codeBits;
        keyString = options.keyString;
        arrayData = options.arrayData;
        mag = Math.max(dataBits, codeBits);
        prev = 0;
        mask = [];
        for (i = 0; i < mag; i += 1) {
            mask.push(prev);
            prev += prev + 1
        }
        max = prev;
        group = dataBits / gcd(dataBits, codeBits)
    };
    gcd = function (a, b) {
        var t;
        while (b !== 0) {
            t = b;
            b = a % b;
            a = t
        }
        return a
    };
    translate = function (input, bitsIn, bitsOut, decoding) {
        var i, len, chr, byteIn, buffer, size, output, write;
        write = function (n) {
            if (!decoding) {
                output.push(keyString.charAt(n))
            } else if (arrayData) {
                output.push(n)
            } else {
                output.push(String.fromCharCode(n))
            }
        };
        buffer = 0;
        size = 0;
        output = [];
        len = input.length;
        for (i = 0; i < len; i += 1) {
            size += bitsIn;
            if (decoding) {
                chr = input.charAt(i);
                byteIn = keyString.indexOf(chr);
                if (chr === pad) {
                    break
                } else if (byteIn < 0) {
                    throw 'the character "' + chr + '" is not a member of ' + keyString;
                }
            } else {
                if (arrayData) {
                    byteIn = input[i]
                } else {
                    byteIn = input.charCodeAt(i)
                }
                if ((byteIn | max) !== max) {
                    throw byteIn + " is outside the range 0-" + max;
                }
            }
            buffer = (buffer << bitsIn) | byteIn;
            while (size >= bitsOut) {
                size -= bitsOut;
                write(buffer >> size);
                buffer &= mask[size]
            }
        }
        if (!decoding && size > 0) {
            write(buffer << (bitsOut - size));
            len = output.length % group;
            for (i = 0; i < len; i += 1) {
                output.push(pad)
            }
        }
        return (arrayData && decoding) ? output : output.join('')
    };
    encode = function (str) {
        str = utf16to8(str);
        var out = "",
            i = 0,
            len = str.length,
            c1, c2, c3, base64EncodeChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
        while (i < len) {
            c1 = str.charCodeAt(i++) & 0xff;
            if (i == len) {
                out += base64EncodeChars.charAt(c1 >> 2);
                out += base64EncodeChars.charAt((c1 & 0x3) << 4);
                out += "==";
                break
            }
            c2 = str.charCodeAt(i++);
            if (i == len) {
                out += base64EncodeChars.charAt(c1 >> 2);
                out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
                out += base64EncodeChars.charAt((c2 & 0xF) << 2);
                out += "=";
                break
            }
            c3 = str.charCodeAt(i++);
            out += base64EncodeChars.charAt(c1 >> 2);
            out += base64EncodeChars.charAt(((c1 & 0x3) << 4) | ((c2 & 0xF0) >> 4));
            out += base64EncodeChars.charAt(((c2 & 0xF) << 2) | ((c3 & 0xC0) >> 6));
            out += base64EncodeChars.charAt(c3 & 0x3F)
        }
        return out
    };
    decode = function (str) {
        var c1, c2, c3, c4;
        var i, len, out;
        var base64DecodeChars = [-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1, -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1, -1, -1];
        len = str.length;
        i = 0;
        out = "";
        while (i < len) {
            do {
                c1 = base64DecodeChars[str.charCodeAt(i++) & 0xff]
            } while (i < len && c1 == -1);
            if (c1 == -1) break;
            do {
                c2 = base64DecodeChars[str.charCodeAt(i++) & 0xff]
            } while (i < len && c2 == -1);
            if (c2 == -1) break;
            out += String.fromCharCode((c1 << 2) | ((c2 & 0x30) >> 4));
            do {
                c3 = str.charCodeAt(i++) & 0xff;
                if (c3 == 61) {
                    out = utf8to16(out);
                    return out
                }
                c3 = base64DecodeChars[c3]
            } while (i < len && c3 == -1);
            if (c3 == -1) break;
            out += String.fromCharCode(((c2 & 0XF) << 4) | ((c3 & 0x3C) >> 2));
            do {
                c4 = str.charCodeAt(i++) & 0xff;
                if (c4 == 61) {
                    out = utf8to16(out);
                    return out
                }
                c4 = base64DecodeChars[c4]
            } while (i < len && c4 == -1);
            if (c4 == -1) break;
            out += String.fromCharCode(((c3 & 0x03) << 6) | c4)
        }
        out = utf8to16(out);
        return out
    };
    utf16to8 = function (str) {
        var out, i, len, c;
        out = "";
        len = str.length;
        for (i = 0; i < len; i++) {
            c = str.charCodeAt(i);
            if ((c >= 0x0001) && (c <= 0x007F)) {
                out += str.charAt(i)
            } else if (c > 0x07FF) {
                out += String.fromCharCode(0xE0 | ((c >> 12) & 0x0F));
                out += String.fromCharCode(0x80 | ((c >> 6) & 0x3F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F))
            } else {
                out += String.fromCharCode(0xC0 | ((c >> 6) & 0x1F));
                out += String.fromCharCode(0x80 | ((c >> 0) & 0x3F))
            }
        }
        return out
    };
    utf8to16 = function (str) {
        var out, i, len, c;
        var char2, char3;
        out = "";
        len = str.length;
        i = 0;
        while (i < len) {
            c = str.charCodeAt(i++);
            switch (c >> 4) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                    out += str.charAt(i - 1);
                    break;
                case 12:
                case 13:
                    char2 = str.charCodeAt(i++);
                    out += String.fromCharCode(((c & 0x1F) << 6) | (char2 & 0x3F));
                    break;
                case 14:
                    char2 = str.charCodeAt(i++);
                    char3 = str.charCodeAt(i++);
                    out += String.fromCharCode(((c & 0x0F) << 12) | ((char2 & 0x3F) << 6) | ((char3 & 0x3F) << 0));
                    break
            }
        }
        return out
    };
    this.encode = encode;
    this.decode = decode;
    construct()
};
window.Base32 = new Nibbler({
    dataBits: 8,
    codeBits: 5,
    keyString: 'ABCDEFGHIJKLMNOPQRSTUVWXYZ234567',
    pad: '='
});
window.Base64 = new Nibbler({
    dataBits: 8,
    codeBits: 6,
    keyString: 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/',
    pad: '='
});
window.JSON = new
    function () {
        var useHasOwn = !!{}.hasOwnProperty;
        var pad = function (n) {
            return n < 10 ? "0" + n : n
        };
        var m = {
            "\b": '\\b',
            "\t": '\\t',
            "\n": '\\n',
            "\f": '\\f',
            "\r": '\\r',
            '"': '\\"',
            "\\": '\\\\'
        };
        var encodeString = function (s) {
            if (/["\\\x00-\x1f]/.test(s)) {
                return '"' + s.replace(/([\x00-\x1f\\"])/g,
                        function (a, b) {
                            var c = m[b];
                            if (c) {
                                return c
                            }
                            c = b.charCodeAt();
                            return "\\u00" + Math.floor(c / 16).toString(16) + (c % 16).toString(16)
                        }) + '"'
            }
            return '"' + s + '"'
        };
        var encodeArray = function (o) {
            var a = ["["],
                b,
                i,
                l = o.length,
                v;
            for (i = 0; i < l; i += 1) {
                v = o[i];
                switch (typeof v) {
                    case "undefined":
                    case "function":
                    case "unknown":
                        break;
                    default:
                        if (b) {
                            a.push(',')
                        }
                        a.push(v === null ? "null" : JSON.encode(v));
                        b = true
                }
            }
            a.push("]");
            return a.join("")
        };
        var encodeDate = function (o) {
            return '"' + o.getFullYear() + "-" + pad(o.getMonth() + 1) + "-" + pad(o.getDate()) + "T&nbsp;" + pad(o.getHours()) + ":" + pad(o.getMinutes()) + ":" + pad(o.getSeconds()) + '"'
        };
        this.encode = function (o) {
            if (typeof o == "undefined" || o === null) {
                return "null"
            } else if (o instanceof Array) {
                return encodeArray(o)
            } else if (o instanceof Date) {
                return encodeDate(o)
            } else if (typeof o == "string") {
                return encodeString(o)
            } else if (typeof o == "number") {
                return isFinite(o) ? String(o) : "null"
            } else if (typeof o == "boolean") {
                return String(o)
            } else {
                var a = ["{"],
                    b,
                    i,
                    v;
                for (i in o) {
                    if (!useHasOwn || o.hasOwnProperty(i)) {
                        v = o[i];
                        switch (typeof v) {
                            case "undefined":
                            case "function":
                            case "unknown":
                                break;
                            default:
                                if (b) {
                                    a.push(',')
                                }
                                a.push(this.encode(i), ":", v === null ? "null" : this.encode(v));
                                b = true
                        }
                    }
                }
                a.push("}");
                return a.join("")
            }
        };
        this.decode = function (json) {
            return eval("(" + json + ')')
        }
    };
String.space = function (len) {
    var t = [],
        i;
    for (i = 0; i < len; i++) {
        t.push(' ')
    }
    return t.join('')
};
function format(msg) {
    var text = msg.split("\n").join(" ");
    var t = [];
    var tab = 0;
    var inString = false;
    for (var i = 0,
             len = text.length; i < len; i++) {
        var c = text.charAt(i);
        if (inString && c === inString) {
            if (text.charAt(i - 1) !== '\\') {
                inString = false
            }
        } else if (!inString && (c === '"' || c === "'")) {
            inString = c
        } else if (!inString && (c === ' ' || c === "\t")) {
            c = ''
        } else if (!inString && c === ':') {
            c += ' '
        } else if (!inString && c === ',') {
            c += "\n" + String.space(tab * 2)
        } else if (!inString && (c === '[' || c === '{')) {
            tab++;
            c += "\n" + String.space(tab * 2)
        } else if (!inString && (c === ']' || c === '}')) {
            tab--;
            c = "\n" + String.space(tab * 2) + c
        }
        t.push(c)
    }
    $("#resp").val(t.join(''))
}
function getRemoveWhiteSpace(msg) {
    var text = msg.split("\n").join(" ");
    var t = [];
    var inString = false;
    for (var i = 0,
             len = text.length; i < len; i++) {
        var c = text.charAt(i);
        if (inString && c === inString) {
            if (text.charAt(i - 1) !== '\\') {
                inString = false
            }
        } else if (!inString && (c === '"' || c === "'")) {
            inString = c
        } else if (!inString && (c === ' ' || c === "\t")) {
            c = ''
        }
        t.push(c)
    }
    $("#resp").val(t.join(''))
}
function isURL(str_url) {
    var strRegex = '^((https|http|ftp|rtsp|mms)?://)';
    var re = new RegExp(strRegex);
    if (re.test(str_url)) {
        return (true)
    } else {
        return (false)
    }
}
$(document).ready(function () {
    $("#sub").click(function (e) {
        var paramData = null;
        if (null != $("#reqParam").val() && $("#reqParam").val().length >= 2) {
            if ($("#isAutoEncode").prop("checked")) {
                paramData = Base64.encode($("#reqParam").val());
            } else {
                paramData = $("#reqParam").val();
            }
        }
        if (!isURL($.trim($("#reqUrl").val()))) {
            return
        }
        $.ajax({
            url: $.trim($("#reqUrl").val()),
            type: $("#reqMethod").val(),
            dataType: "text",
            data: paramData,
            success: function (msg) {
                if ($("#isAutoDecode").prop('checked')) {
                    if ($("#isAutoFormat").prop("checked")) {
                        format(Base64.decode(msg))
                    }else{
                        $("#resp").val(Base64.decode(msg));
                    }
                } else {
                    $("#resp").val(msg);
                }
            },
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $("#resp").val("请求有误");
            }
        })
    });
    $("#decode").click(function (e) {
        $("#resp").val(Base64.decode($("#resp").val()))
    });
    $("#encode").click(function (e) {
        $("#resp").val(Base64.encode($("#resp").val()))
    });
    $("#format").click(function (e) {
        format($("#resp").val())
    });
    $("#removeWhite").click(function (e) {
        getRemoveWhiteSpace($("#resp").val())
    });
    $("#clearLeft").click(function (e) {
        $("#reqUrl").val("");
        $("#reqParam").val("")
    });
    $("#clearRight").click(function (e) {
        $("#resp").val("")
    });
    $("#isAutoDecode").click(function (e) {
        if (!this.checked) {
            $("#isAutoFormat").attr("checked", false)
        }
    });
    $("#isAutoFormat").click(function (e) {
        if (this.checked) {
            $("#isAutoDecode").attr("checked", true)
        }
    });
});