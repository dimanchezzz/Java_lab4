package Classes;

import org.apache.commons.dbcp2.*;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;

import javax.sql.DataSource;

// import org.apache.commons.pool2.ObjectPool;
//  import org.apache.commons.pool2.impl.GenericObjectPool;
//  32 import org.apache.commons.dbcp2.ConnectionFactory;
//  33 import org.apache.commons.dbcp2.PoolableConnection;
//  34 import org.apache.commons.dbcp2.PoolingDataSource;
//  35 import org.apache.commons.dbcp2.PoolableConnectionFactory;
//  36 import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
//  37

public class DBLevel {
    static final String JDBC_DRIVER = "org.postgresql.Driver";
    static final String DATABASE_URL = "jdbc:postgresql://dbtest.ct61ihyxgavq.eu-central-1.rds.amazonaws.com:5432/TestDB";
    static final String DATABASE_USER = "Postgresql";
    static final String DATABASE_PASSWORD = "postgresqlpa";

    // from https://git-wip-us.apache.org/repos/asf?p=commons-dbcp.git;a=blob;f=doc/PoolingDataSourceExample.java;h=2a12c74898930b9623223db1597b8a8052a6f1df;hb=refs/heads/master
    public static DataSource setupDataSource() throws ClassNotFoundException {
        Class.forName(JDBC_DRIVER);
        //
        // First, we'll create a ConnectionFactory that the
        // pool will use to create Connections.
        // We'll use the DriverManagerConnectionFactory,
        // using the connect string passed in the command line
        // arguments.
        //
        ConnectionFactory connectionFactory =
                new DriverManagerConnectionFactory(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);

        //
        // Next we'll create the PoolableConnectionFactory, which wraps
        // the "real" Connections created by the ConnectionFactory with
        // the classes that implement the pooling functionality.
        //
        PoolableConnectionFactory poolableConnectionFactory =
                new PoolableConnectionFactory(connectionFactory, null);

        //
        // Now we'll need a ObjectPool that serves as the
        // actual pool of connections.
        //
        // We'll use a GenericObjectPool instance, although
        // any ObjectPool implementation will suffice.
        //
        ObjectPool<PoolableConnection> connectionPool =
                new GenericObjectPool<>(poolableConnectionFactory);

        // Set the factory's pool property to the owning pool
        poolableConnectionFactory.setPool(connectionPool);

        //
        // Finally, we create the PoolingDriver itself,
        // passing in the object pool we created.
        //
        PoolingDataSource<PoolableConnection> dataSource =
                new PoolingDataSource<>(connectionPool);

        return dataSource;
    }


}
