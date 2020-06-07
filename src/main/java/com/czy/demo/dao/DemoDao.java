package com.czy.demo.dao;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class DemoDao {

    private Configuration configuration;
    private Connection connection;

    public DemoDao(){
        configuration = HBaseConfiguration.create();

        configuration.set("hbase.zookeeper.quorum","10.131.238.35");  // hbase服务器地址
        configuration.set("hbase.zookeeper.property.clientPort","2181"); // 端口号
        try {
            connection = ConnectionFactory.createConnection(configuration);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public float getTopicProByIdAndTopic(String id, int topic, String tableName, String colFamily) throws IOException {
        Table table = connection.getTable(TableName.valueOf(tableName));
        Get get = new Get(id.getBytes());
        String col = "topic" + topic;
        get.addColumn(colFamily.getBytes(), col.getBytes());
        Result result = table.get(get);
        table.close();
        return Float.parseFloat(new String(result.getValue(colFamily.getBytes(), col.getBytes())));
    }
}
