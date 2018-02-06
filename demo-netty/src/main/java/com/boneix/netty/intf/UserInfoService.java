package com.boneix.netty.intf;

import com.boneix.netty.proto.RequestRichManProto;
import com.boneix.netty.proto.RichManProto;

/**
 * Created by rzhang on 2017/5/15.
 */
public interface UserInfoService {
    RichManProto.RichMan findOneRichMan(RequestRichManProto.RequestRichMan arg0);


}
