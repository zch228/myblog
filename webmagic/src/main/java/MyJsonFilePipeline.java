import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MyJsonFilePipeline extends MyFilePersistentBase implements Pipeline {
	private Logger logger;
	/**
	 * 空参构造--使用默认路径
	 */
	public MyJsonFilePipeline(){
		logger = LoggerFactory.getLogger(getClass());
		setPath("/data/webmagic");
	}
	/**
	 * 参数构造--指定路径
	 * @param path
	 */
	public MyJsonFilePipeline(String path) {
		logger = LoggerFactory.getLogger(getClass());
		setPath(path);
	}
	
	@Override
	public void process(ResultItems resultItems, Task task) {
		try{
			String filepath=new StringBuilder().append(this.path).
					append(PATH_SEPERATOR).
					append(filenameByDate()).append(".txt").toString();
			//true---不将原先文件内容覆盖
			PrintWriter printWriter = new PrintWriter(new FileWriter(
					getFile(filepath),true));
			printWriter.write(JSON.toJSONString(resultItems.getAll()));
			printWriter.close();
		} catch (IOException e) {
			logger.warn("write file error", e);
			//System.out.println("文件写入出异常！！！！");
		}
	}
 
}