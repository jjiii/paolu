package jj.tech.paolu;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * 	@SpringBootApplication 是一个方便的注释, 它添加了以下所有内容:
 * 
  	@Configuration 将类标记为应用程序上下文的 bean 定义的源。
  	@EnableAutoConfiguration 告诉 spring 引导开始根据类路径设置、其他 bean 和各种属性设置添加 bean。
     	通常你会为 spring mvc 应用程序添加 @EnableWebMvc, 但是 spring 引导在类路径上看到 spring WebMvc 时会自动添加它。这将应用程序标记为 web 应用程序, 并激活诸如设置 DispatcherServlet 之类的关键行为。
	@ComponentScan 告诉 spring 在 hello 包中查找其他组件、配置和服务, 使其能够找到控制器。
	
	main () 方法使用 spring 引导的 SpringApplication. run () 方法来启动应用程序。
	没有单行 xml？没有任何 web. xml 文件。此 web 应用程序是100% 纯 java, 您不必处理配置任何管道或基础结构。
	还有一个标记为 @Bean 的 CommandLineRunner 方法, 并在启动时运行。它检索所有的 bean, 由您的应用程序创建或自动添加感谢春季启动。它的排序和打印出来。
 * 	@author Dou
 *
 */

@SpringBootApplication
@EnableDiscoveryClient
public class Application {

	//打成war包需要继承
//	@SpringBootApplication
//	public class Application extends SpringBootServletInitializer {
//	
//	@Override
//    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
//        return application.sources(Application.class);
//    }
//	
//	public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//    }
	
	
	public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }
    
    
    
//    @StreamListener(IOrderProcessor.INPUT_ORDER)
//    public void input(Message<A> message) {
//        System.out.println("一般监听收到：" + message.getPayload());
//    }


   
    
//    public JdbcConnectionPool JdbcConnectionPool(){
//    	String url = "jdbc:h2:~/test";
//    	String user = "sa";
//    	String password = "";
//    	return JdbcConnectionPool.create(url, user, password);
//    }
    
    	
//    @Bean
//    @StreamMessageConverter
//    public MessageConverter customMessageConverter() {
//        return new MyCustomMessageConverter();
//    }
	
	
}
