package sapphire.wrapper.ruby;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.net.InetSocketAddress;

import sapphire.appexamples.hankstodo.app.TodoListManager;
import sapphire.oms.OMSServer;
import sapphire.kernel.server.KernelServer;
import sapphire.kernel.server.KernelServerImpl;

public class Wrapper {
        private static OMSServer server;

        public static OMSServer getOMSServer(String ip, int port){
                Registry registry;
                try {
                        registry = LocateRegistry.getRegistry(ip, port);
                        server = (OMSServer) registry.lookup("SapphireOMS");
                        KernelServer nodeServer = new KernelServerImpl(new InetSocketAddress(ip, port),
                    new InetSocketAddress(ip, port));
                } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }

                return server;
        }

        public static TodoListManager getAppEntryPoint(){
                if (server == null) {
                        return null;
                }
                TodoListManager tlm;
                try {
                  tlm = (TodoListManager) server.getAppEntryPoint();
                } catch (Exception e) {
                        return null;
                }
                return tlm;
        }

}
