package com.zc.jdbc;

public class TestJDBC {
	public static void main(String[] args) {
		//主键每次插入记得修改
		/*Student stu=new Student(3,3,"b2","b3","zxx","1",100);
		String sql="insert into student(flowid,type,idcard,examcard,studentname,location,grade) "
				+"values (?,?,?,?,?,?,?)";

		MyTools.update(sql,stu.getFlowid(),stu.getType(),stu.getIdcard()
				,stu.getExamcard(),stu.getStudentname(),stu.getLocation(),stu.getGrade() );*/
		//给每个列名添加别名,该别名是实体类中的属性名
		String sql2="select flowid flowid,type,idcard,examcard,studentname,location,grade from student";
		JdbcTools.get(Student.class,sql2);//5一个参数填充sql中的占位符
	}
}
