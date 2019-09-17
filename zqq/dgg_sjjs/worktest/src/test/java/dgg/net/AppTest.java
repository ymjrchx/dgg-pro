package dgg.net;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.experimental.categories.Categories;
import sun.nio.ch.ThreadPool;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    @Test
    public void httpUrlDo(){
        String url = "http://wtzc2.dggbj.com/bdwyqpc/xmcx?&WTBD-PC&url=xm-zzxm&e_keywordid&{keywordid}&e_keywordid2&110602258138";
       String url1= url.replaceAll("\\?.*","");
        System.out.println(url1);
    }


    @Test
    public void httpUrlTest(){
        Pattern pattern = Pattern.compile("popularize_code=\\d*&");

        String url  = "http://wtzc2.dggbj.com/zc/bdwyqm/xmsb/?popularize_code=10854&&WTBD-MO&url=xm-zjxm&e_keywordid&{keywordid}&e_keywordid2&110602257836";
        Matcher matcher = pattern.matcher(url);
        while(matcher.find()){
            System.out.println(matcher.group());
        }
    }


    @Test
    public void threadTeast() throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newCachedThreadPool();


        ExecutorCompletionService<String> service = new ExecutorCompletionService(executorService);
        for (int i = 0; i <10 ; i++) {
            int ii = i;
            service.submit(()->{
               return Thread.getAllStackTraces().toString() + "dddd"+ii;
            });

        }

        for (int i = 0; i < 10 ; i++) {
            String str = service.take().get();
            System.out.println(str);
        }

        System.out.println("this is the end!");
        executorService.shutdown();

        System.out.println("end threadPool");



    }
}
