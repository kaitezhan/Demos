#!/usr/bin/env python
# -*- coding: utf-8 -*-
# @Time    : 2017/9/19 18:38
# @Author  : boneix

class CheckParamType(object):
    @staticmethod
    def param_check(*ty, **argv):
        ty = map(CheckParamType._to_check_fun, ty)
        argv = dict((i, CheckParamType._to_check_fun(argv[i])) for i in argv)

        def common(fun):
            def deal(*fun_x, **fun_y):
                if ty:
                    x_list = [a for a in fun_x]
                    x_list_it = iter(x_list)
                    result = []
                    for t_check in ty:
                        r = t_check(x_list_it.next())
                        result.append(r)

                if argv:
                    y_dic = dict((i, fun_y[i]) for i in fun_y)
                    result = {}
                    for k in argv.keys():
                        f = argv[k](y_dic.get(k))
                        result[k] = f

                return fun(*fun_x, **fun_y)

            return deal

        return common

    # 用于生成判断具体参数的函数
    @staticmethod
    def _to_check_fun(t):
        return lambda x: isinstance(x, t)


@CheckParamType.param_check(int, str, list)
def fun_1(a, b, c):
    print(a, b , c)


def unit_test():
    fun_1(1, 2, c="aaa")


if __name__ == '__main__':
    unit_test()
