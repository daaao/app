package io.zhijian.base.sql;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class SqlBuilder {

	private static Map<String, String> sqlSet = new HashMap<String, String>();

	public static String buildSql(String sqlFileName, String json) throws IOException, TemplateException {
		String sql;

		// 创建Configuration对象
		Configuration cfg = getConfiguration();

		// 创建Template对象
		Template template = cfg.getTemplate(sqlFileName);
		template.setEncoding("utf-8");

		Map<String, Object> context = new HashMap<String, Object>();
		// 将json字符串加入数据模型
		context.put("params", json);
		// 输出流
		StringWriter writer = new StringWriter();
		// 将数据和模型结合生成sql
		template.process(context, writer);
		// 获得sql
		sql = writer.toString();
		writer.close();
		return sql;
	}

	private static Configuration getConfiguration() throws IOException {
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_23);
		cfg.setDefaultEncoding("UTF-8");

		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		ServletContext servletContext = webApplicationContext.getServletContext();
		String templatePath = servletContext.getRealPath(SqlConfig.PATH);

//		cfg.setServletContextForTemplateLoading(servletContext,SqlConfig.PATH);//web路径
		cfg.setDirectoryForTemplateLoading(new File(templatePath));

		return cfg;
	}
}
