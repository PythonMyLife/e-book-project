package backend.config;

import backend.Service.RmiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.rmi.RemoteException;

@Configuration
public class RmiServer {

    @Autowired
    private RmiService rmiService;

    @Bean
    public RmiServiceExporter getRmiServiceExporter() {
        RmiServiceExporter rmiServiceExporter = new RmiServiceExporter();
        rmiServiceExporter.setServiceName("ebookService");
        rmiServiceExporter.setService(rmiService);
        rmiServiceExporter.setServiceInterface(RmiService.class);
        rmiServiceExporter.setRegistryPort(1099);
        return rmiServiceExporter;
    }
}
