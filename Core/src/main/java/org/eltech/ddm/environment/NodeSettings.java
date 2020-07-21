package org.eltech.ddm.environment;

import org.eltech.ddm.inputdata.MiningInputStream;

/**
 * Created by iihol on 01.03.2018.
 */
public class NodeSettings {

    private String host;
    private int port;
    private int executorsPerNode;
    private MiningInputStream data;

    public NodeSettings(String host, int port, int executorsPerNode){
        this.host = host;
        this.port = port;
        this.executorsPerNode = executorsPerNode;
    }

    public NodeSettings(String host, int port, int executorsPerNode, MiningInputStream data){
        this(host, port, executorsPerNode);
        this.data = data;
    }

    public MiningInputStream getData() {
        return data;
    }

    public int getPort() {
        return port;
    }

    public String getHost() {
        return host;
    }

    public int getExecutorsPerNode() {
        return executorsPerNode;
    }

}
