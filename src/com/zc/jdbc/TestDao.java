package com.zc.jdbc;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class TestDao {
	public static void main(String[] args) throws Exception {
		String sql="select * from student";
		Dao dao=new Dao();
		List<Student> results = dao.getBeanList(Student.class,sql);
		for(Student stu:results){
			System.out.println(stu);
		}
		//
		try {
			List<Map<String, Object>> result = dao.getMapList(sql);
			for(Map<String,Object> map:result){
				for(Entry<String,Object> entry:map.entrySet()){
					System.out.print(entry.getKey()+" "+entry.getValue()+"\t");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
