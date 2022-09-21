package dvdStore;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.intuit.karate.Results;
import com.intuit.karate.Runner;

import book.inface.TestLifecycleLogger;

@DisplayName("RestDvD API Testing")
public class API_IT implements TestLifecycleLogger{
	@Test
    void test() {
		 Results results = Runner.path("classpath:dvd")
	                .outputCucumberJson(true).parallel(1);
	        assertEquals(0, results.getFailCount(), results.getErrorMessages());
    }
}