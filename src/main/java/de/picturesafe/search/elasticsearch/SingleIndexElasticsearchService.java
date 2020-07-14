/*
 * Copyright 2020 picturesafe media/data/bank GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.picturesafe.search.elasticsearch;

import de.picturesafe.search.elasticsearch.config.FieldConfiguration;
import de.picturesafe.search.elasticsearch.model.IndexObject;
import de.picturesafe.search.parameter.AccountContext;
import de.picturesafe.search.elasticsearch.model.ElasticsearchInfo;
import de.picturesafe.search.elasticsearch.model.SearchResult;
import de.picturesafe.search.elasticsearch.model.SuggestResult;
import de.picturesafe.search.expression.Expression;
import de.picturesafe.search.expression.SuggestExpression;
import de.picturesafe.search.parameter.SearchParameter;

import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Service interface to interact with elasticsearch.
 */
@SuppressWarnings("unused")
public interface SingleIndexElasticsearchService {

    /**
     * Gets the wrapped elasticsearch service.
     *
     * @return The wrapped elasticsearch service
     */
    ElasticsearchService getElasticsearchService();

    /**
     * Gets Elasticsearch infos like client and server version.
     *
     * @see ElasticsearchInfo
     * @return  Elasticsearch infos
     */
    ElasticsearchInfo getElasticsearchInfo();

    /**
     * Gets the index alias.
     *
     * @return  The index alias
     */
    String getIndexAlias();

    /**
     * Gets the name of the index mapped to the alias.
     *
     * @return The name of the index mapped by the alias
     */
    String getIndexName();

    /**
     * Creates a new index with alias.
     *
     * @return Name of the new index
     */
    String createIndexWithAlias();

    /**
     * Deletes the index with alias.
     */
    void deleteIndexWithAlias();

    /**
     * Sets the version of the index.
     *
     * @param indexVersion  Version of the index
     */
    void setIndexVersion(int indexVersion);

    /**
     * Gets the version of the index.
     *
     * @return  Version of the index
     */
    int getIndexVersion();

    /**
     * Adds one or more field configurations to the index mapping.
     *
     * @param fieldConfigs  Field configurations to add
     */
    void addFieldConfiguration(FieldConfiguration... fieldConfigs);

    /**
     * Adds a document to the index. If a document with the same ID already exists it will be updated.
     * NOTE: key "id" must be present in document.
     *
     * @param dataChangeProcessingMode  {@link DataChangeProcessingMode}
     * @param document                  Document to be added
     */
    void addToIndex(DataChangeProcessingMode dataChangeProcessingMode, Map<String, Object> document);

    /**
     * Adds an object to the index. If an object with the same ID already exists it will be updated.
     *
     * @param dataChangeProcessingMode  {@link DataChangeProcessingMode}
     * @param object                    Index object to be added
     */
    void addObjectToIndex(DataChangeProcessingMode dataChangeProcessingMode, IndexObject<?> object);

    /**
     * Adds an object to the index. If an object with the same ID already exists it will be updated.
     *
     * @param dataChangeProcessingMode  {@link DataChangeProcessingMode}
     * @param object                    Index object to be added
     * @param id                        ID to assign to the persisted object (useful if the object does not provide an ID itself)
     */
    void addObjectToIndex(DataChangeProcessingMode dataChangeProcessingMode, IndexObject<?> object, Object id);

    /**
     * Adds multiple documents to the index. If a document with the same ID already exists it will be updated.
     * NOTE: key "id" must be present in documents.
     *
     * @param dataChangeProcessingMode  {@link DataChangeProcessingMode}
     * @param documents                 Documents to be added
     */
    void addToIndex(DataChangeProcessingMode dataChangeProcessingMode, List<Map<String, Object>> documents);

    /**
     * Adds multiple objects to the index. If an object with the same ID already exists it will be updated.
     *
     * @param dataChangeProcessingMode  {@link DataChangeProcessingMode}
     * @param objects                   Objects to be added
     */
    void addObjectsToIndex(DataChangeProcessingMode dataChangeProcessingMode, List<IndexObject<?>> objects);

    /**
     * Removes a document from the index.
     *
     * @param dataChangeProcessingMode  {@link DataChangeProcessingMode}
     * @param id                        ID of the document to be removed
     */
    void removeFromIndex(DataChangeProcessingMode dataChangeProcessingMode, Object id);

    /**
     * Removes multiple documents from the index.
     *
     * @param dataChangeProcessingMode  {@link DataChangeProcessingMode}
     * @param ids                       IDs of the documents to be removed
     */
    void removeFromIndex(DataChangeProcessingMode dataChangeProcessingMode, Collection<?> ids);

    /**
     * Removes multiple documents from the index.
     *
     * @param dataChangeProcessingMode  {@link DataChangeProcessingMode}
     * @param expression                Expression defining the search criteria for the documents to delete
     * @param locale                    Locale for searching multilingual fields (use Locale.ROOT if it doesn't matter)
     */
    void removeFromIndex(DataChangeProcessingMode dataChangeProcessingMode, Expression expression, Locale locale);

    /**
     * Searches for documents.
     *
     * @param expression        Expression defining the search criteria
     * @param searchParameter   Parameters for the search execution
     * @return                  {@link SearchResult}
     */
    SearchResult search(Expression expression, SearchParameter searchParameter);

    /**
     * Searches for documents in the context of an user account.
     *
     * @param accountContext    {@link AccountContext} of the current user
     * @param expression        Expression defining the search criteria
     * @param searchParameter   Parameters for the search execution
     * @return                  {@link SearchResult}
     */
    SearchResult search(AccountContext<?> accountContext, Expression expression, SearchParameter searchParameter);

    /**
     * Gets a document from the index.
     *
     * @param id            ID of the documents
     * @return              The document or <code>null</code> if the ID does not exist
     */
    Map<String, Object> getDocument(Object id);

    /**
     * Gets an object from the index.
     *
     * @param id            ID of the object
     * @param type          Type class of the object
     * @param <T>           Generic type of the object
     * @return              The index object or <code>null</code> if the ID does not exist
     */
    <T extends IndexObject<T>> T getObject(Object id, Class<T> type);

    /**
     * Suggests text options for search-as-you-type functionality.
     *
     * @param expressions   Suggest expressions
     * @return              SuggestResult
     */
    SuggestResult suggest(SuggestExpression... expressions);
}
