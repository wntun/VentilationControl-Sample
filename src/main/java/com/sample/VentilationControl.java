package com.sample;

import com.model.Environment;
import com.model.Effect;
import com.model.IAQ;
import com.model.EnergyConsumption;
import com.model.Fan;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.drools.KnowledgeBase;
import org.drools.KnowledgeBaseConfiguration;
import org.drools.KnowledgeBaseFactory;
import org.drools.builder.KnowledgeBuilder;
import org.drools.builder.KnowledgeBuilderFactory;
import org.drools.builder.ResourceType;
import org.drools.conf.EventProcessingOption;
import org.drools.definition.KnowledgePackage;
import org.drools.io.ResourceFactory;
import org.drools.runtime.KnowledgeSessionConfiguration;
import org.drools.runtime.StatefulKnowledgeSession;
import org.drools.runtime.conf.ClockTypeOption;
import org.drools.runtime.rule.FactHandle;
import org.drools.runtime.rule.WorkingMemoryEntryPoint;
import org.drools.time.SessionPseudoClock;

/**
 * This is a sample class to launch a rule.
 */
public class VentilationControl {
	
    private static KnowledgeBase kbase;
    private static StatefulKnowledgeSession ksession;
    private static WorkingMemoryEntryPoint entrypoint;
    private static FactHandle factHandle;
    public static int counter;

    public static final void main(String[] args) {
       init();
    }
    
    public static void init(){
    	 try {
             // load up the knowledge base
         	KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();
             Collection<KnowledgePackage> pkgs;
             
             KnowledgeBaseConfiguration config = KnowledgeBaseFactory.newKnowledgeBaseConfiguration();
             config.setOption(EventProcessingOption.STREAM);
             
             KnowledgeSessionConfiguration sessionConfig = KnowledgeBaseFactory.newKnowledgeSessionConfiguration();
             sessionConfig.setOption(ClockTypeOption.get("pseudo"));

             kbase = KnowledgeBaseFactory.newKnowledgeBase(config);
             kbuilder.add( ResourceFactory.newFileResource("src//main//resources//rules//ControlRules.drl"),ResourceType.DRL);
             		
             if ( kbuilder.hasErrors() ) {
                 System.out.println( kbuilder.getErrors().toString() );
                 throw new RuntimeException( "Unable to compile drl\"." );
             }
             pkgs = kbuilder.getKnowledgePackages(); 
             kbase.addKnowledgePackages( pkgs ); 
             ksession = kbase.newStatefulKnowledgeSession(sessionConfig,null);
             entrypoint = ksession.getWorkingMemoryEntryPoint("entrypoint");
             
             IAQ currentIAQ = new IAQ(1, 200, new Date());
             Environment environment = new Environment(currentIAQ, new Effect(10f), 
                     new Fan(1), new EnergyConsumption(0));
             insertFact(environment);
             
         } catch (Throwable t) {
             t.printStackTrace();
         }
    }
    
    public static void insertFact(Environment environment){
        SessionPseudoClock clock = ksession.getSessionClock();      
        counter = counter + 1;
        clock.advanceTime(counter, TimeUnit.MINUTES);
        if(factHandle == null){ // new environment object 
            factHandle = entrypoint.insert(environment);
        }
        else{
            entrypoint.update(factHandle, environment);
        }
       
        ksession.fireAllRules();        
    }
    
    


}