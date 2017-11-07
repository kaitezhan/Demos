#!/user/bin/env python3
# -*- coding: utf-8 -*-
from multiprocessing.pool import Pool


class TaskAction(object):
    def do_in_action(self):
        pass


class TaskProcess(object):
    def __init__(self, core_size=None):
        if core_size is None:
            self.pool = Pool()
        else:
            if type(core_size) is not int:
                raise ValueError('core_size can\'t handle type  %s ,only int support ' % type(core_size))
            self.pool = Pool(core_size)

    # 检查task参数有效性
    @staticmethod
    def _check_task(tasks):
        # check tasks type is list ?
        if type(tasks) is not list:
            raise ValueError('tasks can\'t handle type %s , only list support' % type(tasks))
        # check tasks' subset is TaskAction ?
        for child_task in tasks:
            if not isinstance(child_task, TaskAction):
                raise ValueError(
                    'tasks\' subset can\'t handle type %s , only TaskAction support' % type(child_task))

    # 异步执行进程
    def execute_task(self, tasks):
        apply_results = []
        # check params
        self._check_task(tasks)
        # 添加任务至进程池
        for child_task in tasks:
            apply_results.append(self.pool.apply_async(child_task.do_in_action))
        # 拒绝添加新的Process
        self.pool.close()
        # 等待所有子进程执行完毕
        self.pool.join()
        # 封装请求结果
        results = []
        for res in apply_results:
            results.append(res.get())
        return results
