package mk.LoanAnalyzer;

import com.vaadin.flow.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableVaadin
public class LoanAnalyzerApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanAnalyzerApplication.class, args);
	}

}
