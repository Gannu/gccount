package eccount;

import eccount.EccountQueryBuilder;
import com.deerwalk.das.elasticsearch.rest.action.RequestState;
import com.deerwalk.das.elasticsearch.rest.action.SummaryQueryBuilder;
import com.deerwalk.das.elasticsearch.rest.action.support.*;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.AndFilterBuilder;
import org.elasticsearch.index.query.FilterBuilder;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.RangeFilterBuilder;
import org.elasticsearch.rest.RestRequest;
import org.elasticsearch.search.facet.FacetBuilder;
import org.elasticsearch.search.facet.FacetBuilders;
import org.elasticsearch.search.facet.statistical.StatisticalFacetBuilder;
import org.elasticsearch.search.facet.termsstats.TermsStatsFacetBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TransactionAnalysisQueryBuilder implements EccountQueryBuilder {

	protected String[] periods;
        protected Map<Long, Long> ranges;
	Logger logger = LoggerFactory.getLogger(TransactionAnalysisQueryBuilder.class.getName());


    @Override
    public SearchRequestBuilder query(ElasticReqaust request, Client client) {
        logger.info(""+buildQuery(request, client));
        return buildQuery(request, client);
    }


    protected SearchRequestBuilder buildQuery(ElasticRequest request, Client client) {
        periods = getRequestRange(state);
        ranges  = getPeriods(state, periods);
        FilterBuilder filter = null;
        SearchRequestBuilder builder = buildTrendQuery(request, client, periods[0], periods[1], filter);

        builder.addField("total");
        builder.setTypes("Transaction");
        logger.info("builder = " + builder);
        return builder;
    }

    public static String[] getRequestRange(ClientRequest state) {
        String paramFrom = state.request.param(state.period() + "From");
        String paramTo = state.request.param(state.period() + "To");
        return new String[]{paramFrom, paramTo};
    }

    public static Map<Long, Long> getPeriods(final ClientRequest state, String[] periods) {
        if (periods == null) periods = getRequestRange(state);
        Map<Long, Long> ranges = new HashMap<Long, Long>() {
            {
                put(state.request.param(state.period() + "From"), state.request.param(state.period() + "To"));
            }
        };
        return ranges;
    }

    protected SearchRequestBuilder buildTrendQuery(ClientRequest state, Client client, Object periodFrom, Object periodTo, FilterBuilder filter, String... types) {
        return new SearchRequestBuilder();
    }
}
