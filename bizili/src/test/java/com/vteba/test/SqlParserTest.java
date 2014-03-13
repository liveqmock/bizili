package com.vteba.test;

import java.util.List;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;
import com.alibaba.druid.util.JdbcUtils;

public class SqlParserTest {
	public SqlParserTest() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
//		String sql = "select p, s.count as views, " +
//				"(select count(*) from Comments rc where rc.linkedId=p.id and rc.classcode='InfoPublishs') as commentNumber," +
//				" (select count(*) from CollectIndexs rci where rci.toId=p.id and rci.classcode='InfoPublishs' and rci.type='favorite') as favorite" +
//				" FROM InfoPublishs p,UserScores s where p.id=s.linkedId and p.userInfo.id=s.userInfo.id and s.classCode='InfoPublishs' AND p.status=?1 ORDER BY p.createtime DESC";
		StringBuilder sqlsb = new StringBuilder(" select distinct rr.* from emp_user uu,user_role ur,roles rr ");
		sqlsb.append(" where uu.user_id = ur.user_id ");
		sqlsb.append(" and ur.role_id = rr.role_id ");
		sqlsb.append(" and uu.user_id = ? ");
		String sql = sqlsb.toString();
		StringBuffer select = new StringBuffer();
		StringBuffer from = new StringBuffer();
		StringBuffer where = new StringBuffer();
		// parser得到AST
		SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, JdbcUtils.MYSQL);
		
		List<SQLStatement> stmtList = parser.parseStatementList(); //
		// 将AST通过visitor输出
		SQLASTOutputVisitor visitor = SQLUtils.createFormatOutputVisitor(from,
				stmtList, JdbcUtils.MYSQL);
		SQLASTOutputVisitor whereVisitor = SQLUtils.createFormatOutputVisitor(
				where, stmtList, JdbcUtils.MYSQL);
		for (SQLStatement stmt : stmtList) {
			// stmt.accept(visitor);
			if (stmt instanceof SQLSelectStatement) {
				SQLSelectStatement sstmt = (SQLSelectStatement) stmt;
				SQLSelect sqlselect = sstmt.getSelect();
				
				SQLSelectQueryBlock query = (SQLSelectQueryBlock) sqlselect.getQuery();
				query.getFrom().accept(visitor);
				query.getWhere().accept(whereVisitor);
				
			}
			System.out.println(from.toString());
			System.out.println(select);
			System.out.println(where);
		}
	}
}