




import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.jcr.Session;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Reference;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.commons.json.JSONArray;
import org.apache.sling.commons.json.JSONObject;
import org.apache.sling.commons.scheduler.Scheduler;
import org.apache.sling.jcr.api.SlingRepository;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.day.cq.dam.api.Asset;


/**
 *  This service executes scheduled jobs
 *  
 */
@Component(immediate=true)
public class IpadAssetPurgeScheduler {

    /** Default log. */
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    /** The scheduler for rescheduling jobs. */
    @Reference
    private Scheduler scheduler;
    @Reference
    private ResourceResolverFactory resourceResolverFactory; 
    
    private Session jcrSession = null;
    
    @Reference
	private SlingRepository repository;
    private String ipadRoot = "/content/dam/mobile/tablet/ipad/content/pub";

    protected void activate(ComponentContext componentContext) throws Exception {
        //case 1: with addJob() method: executes the job every minute
    	jcrSession = repository.loginAdministrative(repository.getDefaultWorkspace());
    	String schedulingExpression = "0 * * * * ?";
        String jobName1 = "case1";
        Map<String, Serializable> config1 = new HashMap<String, Serializable>();
        boolean canRunConcurrently = false;
        log.info("Into activate method ---->");
        final Runnable job1 = new Runnable() {
            public void run() {
            	log.info("Executing a cron job (IpadAssetPurgeScheduler)");
                
        		if(ipadRoot!=null)
        		{
        			try
        			{
        				Asset statusResource = resourceResolverFactory.getAdministrativeResourceResolver(null).resolve(ipadRoot+"/status.json").adaptTo(Asset.class);
        				if(statusResource!=null)
        				{
        					InputStream is = statusResource.getOriginal().getStream();    
        			        Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        			        StringBuffer buf = new StringBuffer();
        			        while (s.hasNext()) {
        			            buf.append(s.next());
        			        }
        			        //log.error(arg0)
        			        JSONObject statusObject = new JSONObject(buf.toString());
        			        JSONArray feedArray = statusObject.getJSONArray("feeds");
        			        List<String> feedList = new LinkedList<String>();
        			        for(int i=0;i<feedArray.length();i++)
        			        {
        			        	JSONObject feedObject = feedArray.getJSONObject(i);
        			        	if(feedObject.has("url"))
        			        	{
        			        		feedList.add(feedObject.getString("url"));
        			        	}
        			        }
        			        Resource ipadResource = resourceResolverFactory.getAdministrativeResourceResolver(null).resolve(ipadRoot);
        			        Iterator<Resource> itr = ipadResource.listChildren();
        			        while(itr.hasNext())
        			        {
        			        	Resource res = itr.next();
        			        	String resName = res.getName();
        			        	if(resName.endsWith(".zip"))
        			        	{
        			        		if(!feedList.contains(resName))
        			        		{
        			        			log.info("Removing ipad resources at path --->"+res.getPath());
        			        			//resourceResolverFactory.getAdministrativeResourceResolver(null).delete(res);
        			        			jcrSession.removeItem(res.getPath());
        			        		}
        			        		else
        			        		{
        			        			log.info("Match Found -------------------------->"+res.getPath());
        			        		}
        			        	}
        			        }
        			        jcrSession.save();
        				}
        			}catch( Exception exception)
        			{
        				log.error("Error occured while purging iPad Content ---->"+exception);
        				exception.printStackTrace();
        			}
        			
        		}
        		else
        		{
        			log.info("Ipad Asset path is null ----> Not purging ipad assets");
        		}
            }
        };
        try {
        	log.info("Into try block ----->");
            this.scheduler.addJob(jobName1, job1, config1, schedulingExpression, canRunConcurrently);
            log.info("After scheduler ------->");
        } catch (Exception e) {
        	log.error("exception occured while starting scheduler -->"+e);
        	e.printStackTrace();
            job1.run();
        }
        
    }

    protected void deactivate(ComponentContext componentContext) {
        log.info("Deactivated, goodbye!");
        if(jcrSession!=null)
        {
        	jcrSession.logout();
        }
    }

}