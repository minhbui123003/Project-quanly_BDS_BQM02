package com.javaweb.repository.custom.impl;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.custom.CustomerRepositoryCustom;
import com.javaweb.utils.NumberUtils;
import com.javaweb.utils.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@Transactional

public class CustomerRepositoryImpl implements CustomerRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

//    hàm join các table
    public static  void joinTable(Map<String,Object> params, StringBuilder sql){
        String staffId = String.valueOf(params.get("managementStaff"));
        if(StringUtils.check(staffId) && staffId != "null"){
            sql.append(" inner join assignmentcustomer on customer.id = assignmentcustomer.id ");
        }
    }

//    hàm truy vấn câu lệnh thông thường

    public static void queryNomal( Map<String,Object> params, StringBuilder sql){
        for(Map.Entry<String,Object> it:params.entrySet()){
            if(!it.getKey().equals("managementStaff") && !it.getKey().startsWith("d")){
                String value = String.valueOf(it.getValue());
                if(StringUtils.check(value)){
                    if(NumberUtils.isLong(value)){
                        sql.append(" AND customer."+it.getKey() + " = " +value +" " );
                    }
                    else
                    {
                        sql.append(" AND customer."+it.getKey() + " LIKE '%" +value +"%' ") ;
                    }
                }
            }
        }
    }

//    ham truy vấn câu lệnh đặc biệt

    public static  void querySpecial( Map<String,Object> params, StringBuilder sql){
        String staffId = String.valueOf(params.get("managementStaff"));
        if(StringUtils.check(staffId) && staffId != "null"){
            sql.append(" and assignmentcustomer.staffid = "+staffId+" " );
        }
    }


    @Override
    public List<CustomerEntity> findAllCustomers(Map<String, Object> params, Pageable pageable) {
        System.out.println("Connecting with database customer");

        StringBuilder sql = new StringBuilder(" select customer.* from customer ");
        joinTable(params, sql);

        StringBuilder where = new StringBuilder("  where 1 = 1  ");
        queryNomal(params, where);
        querySpecial(params, where);
        where.append(" group by customer.id ");
        sql.append(where);
        sql.append(" limit  ").append(pageable.getPageSize()).append(" \n ").append(" offset ").append(pageable.getOffset());
        System.out.println("Final query: " + sql.toString());
        Query query = entityManager.createNativeQuery(sql.toString(),CustomerEntity.class);
        return query.getResultList();

    }

    @Override
    public int countTotalItem(Map<String, Object> params) {
        StringBuilder sql = new StringBuilder(" select customer.* from customer  ");
        joinTable(params, sql);

        StringBuilder where = new StringBuilder("  where 1 = 1  ");
        queryNomal(params, where);
        querySpecial(params, where);
        where.append(" group by customer.id ");
        sql.append(where);

        Query query = entityManager.createNativeQuery(sql.toString(),CustomerEntity.class);
        return query.getResultList().size();
    }

    @Override
    public void insert(Long staffid, Long customerid) {
        String sql = "INSERT INTO assignmentcustomer (staffid, customerid) VALUES("+ staffid +","+customerid+")";
        Query query = entityManager.createNativeQuery(sql);
        query.executeUpdate();
    }
}
