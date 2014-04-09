package com.vteba.test;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.druid.sql.SQLUtils;
import com.alibaba.druid.sql.ast.SQLExpr;
import com.alibaba.druid.sql.ast.SQLStatement;
import com.alibaba.druid.sql.ast.statement.SQLColumnDefinition;
import com.alibaba.druid.sql.ast.statement.SQLSelect;
import com.alibaba.druid.sql.ast.statement.SQLSelectItem;
import com.alibaba.druid.sql.ast.statement.SQLSelectQuery;
import com.alibaba.druid.sql.ast.statement.SQLSelectQueryBlock;
import com.alibaba.druid.sql.ast.statement.SQLSelectStatement;
import com.alibaba.druid.sql.parser.SQLExprParser;
import com.alibaba.druid.sql.parser.SQLParserUtils;
import com.alibaba.druid.sql.parser.SQLStatementParser;
import com.alibaba.druid.sql.visitor.SQLASTOutputVisitor;

public class SqlParserTest {

	public SqlParserTest() {
		
	}

	public static void main(String[] args) {

		//String sql = "select p, s.count as views, (select count(*) from Comments rc where rc.linkedId=p.id and rc.classcode='InfoPublishs') as commentNumber, (select count(*) from CollectIndexs rci where rci.toId=p.id and rci.classcode='InfoPublishs' and rci.type='favorite') as favorite FROM InfoPublishs p,UserScores s where p.id=s.linkedId and p.userInfo.id=s.userInfo.id and s.classCode='InfoPublishs' AND p.status=? ORDER BY p.createtime DESC";
		String sql = "select u.*, u.userName as userName, (u.age + 1) as ageAge, a.city, a.street from user u, address a";
		//String sql = "select nextValue('seq') seq";
		
		StringBuffer select = new StringBuffer();
		StringBuffer column = new StringBuffer();
		StringBuffer from = new StringBuffer();
		StringBuffer where = new StringBuffer();
		//SQLExprParser sqlExprParser = SQLParserUtils.createExprParser(sql, "mysql");
		//SQLColumnDefinition sqlColumnDefinition = sqlExprParser.parseColumn();
		
		select(sql, "mysql");
		
		// parser得到AST
		SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, "mysql");
		List<SQLStatement> stmtList = parser.parseStatementList(); //

		// 将AST通过visitor输出
		SQLASTOutputVisitor visitor = SQLUtils.createFormatOutputVisitor(from, stmtList, "mysql");
		SQLASTOutputVisitor whereVisitor = SQLUtils.createFormatOutputVisitor(where, stmtList, "mysql");
		SQLASTOutputVisitor selectVisitor = SQLUtils.createFormatOutputVisitor(select, stmtList, "mysql");
		
		List<SQLSelectItem> selectItems = new ArrayList<SQLSelectItem>();

		for (SQLStatement stmt : stmtList) {
			//stmt.accept(visitor);
			if (stmt instanceof SQLSelectStatement) {
				SQLSelectStatement sstmt = (SQLSelectStatement) stmt;
				SQLSelect sqlselect = sstmt.getSelect();
				SQLSelectQueryBlock query = (SQLSelectQueryBlock) sqlselect.getQuery();

				query.getFrom().accept(visitor);
				SQLExpr sqlExpr = query.getWhere();
				if (sqlExpr != null) {
					sqlExpr.accept(whereVisitor);
				}
				selectItems = query.getSelectList();
			}
		}

		for (SQLSelectItem selectItem : selectItems) {
			System.out.println(selectItem.getAlias());
			selectItem.accept(selectVisitor);
		}
		
		System.out.println(select);
		System.out.println(from);
		System.out.println(where);
	}
	
	public static List<String> select(String sql, String dbType) {
		SQLStatementParser parser = SQLParserUtils.createSQLStatementParser(sql, dbType);
		SQLSelectStatement sqlSelectStatement = parser.parseSelect();
		SQLSelect sqlSelect = sqlSelectStatement.getSelect();
		SQLSelectQueryBlock sqlSelectQuery = (SQLSelectQueryBlock) sqlSelect.getQuery();
		
//		sqlSelectQuery.getFrom();
//		
//		sqlSelectQuery.getSelectList();
		
		List<String> alises = new ArrayList<String>();
		
		List<SQLSelectItem> sqlSelectItems = sqlSelectQuery.getSelectList();
		for (SQLSelectItem selectItem : sqlSelectItems) {
			if (selectItem.getAlias() != null) {
				alises.add(selectItem.getAlias());
			} else {
				String column = selectItem.toString();
				if (column.contains(".")) {
					column = column.substring(column.indexOf(".") + 1);
				}
				alises.add(column);
			}
		}
		
		return alises;
	}
}
