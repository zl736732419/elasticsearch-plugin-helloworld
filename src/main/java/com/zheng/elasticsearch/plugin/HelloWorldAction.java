package com.zheng.elasticsearch.plugin;

import org.elasticsearch.client.node.NodeClient;
import org.elasticsearch.common.Table;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.rest.BytesRestResponse;
import org.elasticsearch.rest.RestController;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.rest.action.cat.AbstractCatAction;
import org.elasticsearch.rest.action.cat.RestTable;

/**
 * @Author zhenglian
 * @Date 2018/8/30 22:34
 */
public class HelloWorldAction extends AbstractCatAction {
    public HelloWorldAction(Settings settings, RestController controller) {
        super(settings);
        controller.registerHandler(RestRequest.Method.GET, "/_say/helloWorld", this);
    }

    @Override
    protected RestChannelConsumer doCatRequest(RestRequest restRequest, NodeClient nodeClient) {
        String message = restRequest.param("message", "helloWorld");
        Table table = getTableWithHeader(restRequest);
        table.startRow();
        table.addCell(message);
        table.endRow();
        return (channel) -> {
            try {
                channel.sendResponse(RestTable.buildResponse(table, channel));
            }catch (Exception e) {
                channel.sendResponse(new BytesRestResponse(channel, e));
            }
        };
    }

    @Override
    protected void documentation(StringBuilder sb) {
        sb.append(documentation());
    }
    
    private String documentation() {
       return "_say/helloWorld\n"; 
    }

    @Override
    protected Table getTableWithHeader(RestRequest restRequest) {
        Table table = new Table();
        table.startHeaders();
        table.addCell("words", "");
        table.endHeaders();
        return table;
    }

    @Override
    public String getName() {
        return "elasticsearch-plugin-helloworld";
    }
}
