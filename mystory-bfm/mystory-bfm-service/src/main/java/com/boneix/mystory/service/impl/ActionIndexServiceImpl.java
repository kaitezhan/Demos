package com.boneix.mystory.service.impl;

import com.boneix.base.util.JsonUtils;
import com.boneix.base.util.StringUtils;
import com.boneix.mystory.client.ElasticSearchClient;
import com.boneix.mystory.domain.ActionDocument;
import com.boneix.mystory.service.ActionIndexService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.jboss.netty.util.internal.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangrong5 on 2016/9/23.
 */
@Service
public class ActionIndexServiceImpl implements ActionIndexService {

    private static final Logger LOG = LoggerFactory.getLogger(ActionIndexServiceImpl.class);

    private static final String INDEX_NAME="action-index";

    private static final String INDEX_TYPE="ACTION";

    private static final int INDEX_SIZE=10;

    @Resource
    private ElasticSearchClient esClient;

    @Override
    public ActionDocument queryActionDocumentByActionId(int actionId) {

        ActionDocument actionDocument=new  ActionDocument();
        try {
            Client client=esClient.getClient();
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            boolQueryBuilder.must(new TermQueryBuilder("actionId",actionId));
            SearchResponse searchResponse = client.prepareSearch(INDEX_NAME)
                    .setTypes(INDEX_TYPE)
                    .setQuery(boolQueryBuilder)
                    .setExplain(true)
                    .execute()
                    .actionGet();

            SearchHits hits = searchResponse.getHits();

            if(hits.getTotalHits()==1){
                for(SearchHit hit :hits){
                    actionDocument= JsonUtils.toBean(hit.getSource(),ActionDocument.class);
                    break;
                }
            }
        } catch (Exception e) {
            LOG.error("连接elasticsearch失败,Exception :{}",e);
        }
        return actionDocument;
    }

    private BoolQueryBuilder createQueryBulider(Map<String, Object> request) {
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();

        // 产品
        if (request.get("productId") != null) {
            boolQueryBuilder.must(new TermQueryBuilder("productId", request.get("productId")));
        }

        // 产品名，需要模糊查询
        if (request.get("name") != null) {
            boolQueryBuilder.must(new MatchQueryBuilder("productName", request.get("name")).type(MatchQueryBuilder.Type.PHRASE));
        }

        // 团期
        String startDate = request.get("departureBeginDate") == null ? null : String.valueOf(request.get("departureBeginDate")).split(" ")[0];
        String endDate = request.get("departureEndDate") == null ? null : String.valueOf(request.get("departureEndDate")).split(" ")[0];
        if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
            boolQueryBuilder.must(new NestedQueryBuilder("bookCityCalendar.saleCalendar",
                    new RangeQueryBuilder("bookCityCalendar.saleCalendar.departDate").gte(startDate).lte(endDate)));
        } else if (!StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
            boolQueryBuilder.must(new NestedQueryBuilder("bookCityCalendar.saleCalendar",
                    new RangeQueryBuilder("bookCityCalendar.saleCalendar.departDate").from(startDate)));
        } else if (StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
            boolQueryBuilder.must(new NestedQueryBuilder("bookCityCalendar.saleCalendar",
                    new RangeQueryBuilder("bookCityCalendar.saleCalendar.departDate").to(endDate)));
        }


        //多个产品经理
        if (request.get("mangers") != null) {
            boolQueryBuilder.must(new TermsQueryBuilder("productManagerId", request.get("mangers")));
        }

        // 标签
        List<String> labelList = JsonUtils.toBean(request.get("proLabelList"), ArrayList.class);
        if ((request.get("proLabelList") != null && !labelList.isEmpty()) || request.get("profitLabel") != null) {
            BoolQueryBuilder childQueryBuilder = new BoolQueryBuilder();
            // 推荐标签
            if (request.get("proLabelList") != null && !labelList.isEmpty()) {
                BoolQueryBuilder labelQueryBuilder = new BoolQueryBuilder();
                labelQueryBuilder.must(new TermsQueryBuilder("labelInfo.labelId", labelList));
                if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
                    labelQueryBuilder.must(new RangeQueryBuilder("labelInfo.departDate").gte(startDate).lte(endDate));
                } else if (!StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
                    labelQueryBuilder.must(new RangeQueryBuilder("labelInfo.departDate").from(startDate));
                } else if (StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
                    labelQueryBuilder.must(new RangeQueryBuilder("labelInfo.departDate").to(endDate));
                }
                childQueryBuilder.should(new NestedQueryBuilder("labelInfo", labelQueryBuilder));
            }
            // 高利润标签
            if (request.get("profitLabel") != null) {
                BoolQueryBuilder profitQueryBuilder = new BoolQueryBuilder();
                profitQueryBuilder.must(new TermQueryBuilder("profitInfo.isProfit", 1));
                if (!StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
                    profitQueryBuilder.must(new RangeQueryBuilder("profitInfo.departDate").gte(startDate).lte(endDate));
                } else if (!StringUtils.isEmpty(startDate) && StringUtils.isEmpty(endDate)) {
                    profitQueryBuilder.must(new RangeQueryBuilder("profitInfo.departDate").from(startDate));
                } else if (StringUtils.isEmpty(startDate) && !StringUtils.isEmpty(endDate)) {
                    profitQueryBuilder.must(new RangeQueryBuilder("profitInfo.departDate").to(endDate));
                }
                childQueryBuilder.should(new NestedQueryBuilder("profitInfo", profitQueryBuilder));
            }
            boolQueryBuilder.must(childQueryBuilder);
        }
        return boolQueryBuilder;
    }
}
