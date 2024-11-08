package com.javaweb.enums;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public enum TransactionType {
    CSKH("Chăm sóc khách hàng"),
    DDX("Dẫn đi xem") ,
    MN("Mua Nhà"),
    BANK("Thanh toán xong");
    private final String name ;
    TransactionType(String name){ this.name = name ; }

    public String getName() {
        return name;
    }

    public static Map<String,String> transactionType(){
        Map<String,String> listType = new TreeMap<>();
        for(TransactionType item : TransactionType.values()){
            listType.put(item.toString() , item.name);
        }
        return listType;
    }
}
