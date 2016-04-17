package functional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import org.junit.rules.ExternalResource;

public class KnownDatabaseState extends ExternalResource {
    private static final String CONNECTION_STRING = 
            "jdbc:mysql://192.168.0.30:3306/ocs?allowMultiQueries=true";
    private static final String USER_NAME = "user";
    private static final String PASSWORD = "user";
    private static final String BACKUP_FILE = 
            "functional_tests/test_database2.sql";
    
    private static String cachedBackupFile;

    @Override
    protected void before() throws Throwable {
        super.before(); 
        
        long time = System.currentTimeMillis();
        try(Connection connection = 
                DriverManager.getConnection(CONNECTION_STRING, USER_NAME, PASSWORD)) {
            connection.setAutoCommit(false);
            connection.createStatement().execute(getBackupSql());
            connection.commit();
        }
        time = System.currentTimeMillis() - time;
        System.out.println("Restoring DB took " + time + "ms");
    }
    
    private synchronized String getBackupSql() throws IOException {
        if (cachedBackupFile == null) {
            cachedBackupFile = new String(Files.readAllBytes(Paths.get(BACKUP_FILE)), 
                StandardCharsets.UTF_8);
        }
        
        return cachedBackupFile;
    }
}
