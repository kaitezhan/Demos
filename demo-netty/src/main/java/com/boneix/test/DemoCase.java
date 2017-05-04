package com.boneix.test;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by rzhang on 2017/3/6.
 */
public class DemoCase {

    private static Logger logger = LoggerFactory.getLogger(DemoCase.class);

    @Test
    public void test() {
        fail("Not yet implemented");
    }

    @Test
    public void testArrayDelete(){
        List<Student> stus=new ArrayList<Student>();
        for(int i=1;i<10;i++){
            stus.add(new Student(i,"Stu_"+i));
        }
        for(Student student:stus){
            if(student.getId()==2){
               // System.out.println(Thread.currentThread().getId());
                stus.remove(student);
            }
        }




    }
}
