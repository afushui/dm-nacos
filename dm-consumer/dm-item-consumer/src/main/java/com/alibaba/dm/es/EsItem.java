package com.alibaba.dm.es;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDate;

@Data
@Document(indexName = "dm")
public class EsItem {

    @Id
    private Long id;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String itemname;
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String abstractmessage;
    @Field(type = FieldType.Integer)
    private Long cinemaid;
    @Field(type = FieldType.Double)
    private Double minprice;
    @Field(type = FieldType.Double)
    private Double maxprice;
    @Field(type = FieldType.Keyword)
    private String itemtype1name;
    @Field(type = FieldType.Keyword)
    private String itemtype2name;
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private LocalDate starttime;
    @Field(type = FieldType.Date, format = DateFormat.date_optional_time)
    private String endtime;
    @Field(type = FieldType.Keyword)
    private String adress;
    @Field(type = FieldType.Keyword)
    private String areaname;
    @Field(type = FieldType.Text)
    private String imgurl;
    @Field(type = FieldType.Text)
    private String longitude;
    @Field(type = FieldType.Text)
    private String latitude;
    @Field(type = FieldType.Keyword)
    private String address;


}
