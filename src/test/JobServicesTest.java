import services.jobs.JobServices;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;
import static org.junit.runners.Parameterized.*;

public class JobServicesTest {

    @Parameter
    public String name;

    @Parameter (value = 1)
    public String description;

    @Parameter (value = 2)
    public int salary;

    @Parameters
    public static Collection<Object[]> data() {
        Object[][] data = new Object[][] {
                { "Ingeniero" , "soluciona problemas", 46546546 },
                { "Arquitecta" , "diseña edificos", 46546546 },
                { "Futbolistas" , "juega futbol", 46546546 },
                { "Ingeniero de software" , "crea softwre", 46546546 },
               };
        return Arrays.asList(data);
    }

    @org.junit.Test
    public void testCreateJob() throws Exception {
        JobServices jobServices = new JobServices();
        assertNotEquals(null, jobServices.createJob(name, description, salary));
    }

    @org.junit.Test
    public void testReadAllJobs() throws Exception {
        JobServices jobServices = new JobServices();
        assertEquals(data().size(), jobServices.readAllJobs().size());
    }

    @org.junit.Test
    public void testReadJobFeatures() throws Exception {

    }

    @org.junit.Test
    public void testUpdateJob() throws Exception {

    }

    @org.junit.Test
    public void testDeleteJob() throws Exception {
        JobServices jobServices = new JobServices();
        assertNotEquals(false, jobServices.deleteJob(0));
        assertNotEquals(false, jobServices.deleteJob(1));
        assertNotEquals(false, jobServices.deleteJob(2));
        assertNotEquals(false, jobServices.deleteJob(3));
    }
}