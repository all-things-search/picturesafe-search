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

package de.picturesafe.search.elasticsearch.connect.dto;

import de.picturesafe.search.util.logging.CustomJsonToStringStyle;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Map;

public class SearchHitDto {

    private final String id;
    private final Map<String, Object> attributes;
    private Map<String, List<SearchHitDto>> innerHits;

    public SearchHitDto(String id, Map<String, Object> attributes) {
        this.id = id;
        this.attributes = attributes;
    }

    public String getId() {
        return id;
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public Object get(String fieldName) {
        return attributes.get(fieldName);
    }

    public int size() {
        return attributes.size();
    }

    public Map<String, List<SearchHitDto>> getInnerHits() {
        return innerHits;
    }

    public SearchHitDto innerHits(Map<String, List<SearchHitDto>> innerHits) {
        this.innerHits = innerHits;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, new CustomJsonToStringStyle()) //--
                .append("id", id) //--
                .append("attributes", attributes) //--
                .append("innerHits", innerHits) //--
                .toString();
    }
}
