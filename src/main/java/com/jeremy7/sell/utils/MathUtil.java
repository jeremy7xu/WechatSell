package com.jeremy7.sell.utils;

public class MathUtil {

    /*
    * 比较两个金额是否相等
    *
    *
    *
    * */
        private  static  final  Double MONEY_RANGE = 0.01;
        public  static  Boolean equals(Double d1, Double d2){


            Double result = Math.abs(d1-d2);
            if(result <MONEY_RANGE){
                return  true;

            }else {

                return  false;
            }






        }

}
