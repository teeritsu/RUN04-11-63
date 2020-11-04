package com.example.apprunner;

public class UserListResponse {

    private String name_event;
    private String name_producer;
    String date_reg_start,date_reg_end,detail;

    public UserListResponse(String name_event,String name_producer){
        this.name_event = name_event;
        this.name_producer = name_producer;
    }

    public  String getName_event(){
        return name_event;
    }
    public void setName_event(){
        this.name_event = name_event;
    }
    public  String getName_producer(){
        return name_producer;
    }
    public  void setName_producer(){
        this.name_producer = name_producer;
    }
    public String date_reg_start(){
        return date_reg_start;
    }
    public String date_reg_end(){
        return date_reg_end;
    }
    public String getDetail(){ return detail; }

}
