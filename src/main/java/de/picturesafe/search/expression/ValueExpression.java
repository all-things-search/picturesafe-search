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

package de.picturesafe.search.expression;

import de.picturesafe.search.util.logging.CustomJsonToStringStyle;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static de.picturesafe.search.expression.ConditionExpression.Comparison.EQ;

/**
 * Expression to match values on fields
 */
public class ValueExpression extends ConditionExpression implements BoostableExpression<ValueExpression> {

    private Object value;
    private boolean matchPhrase = false;
    private Float boost;

    /**
     * Constructor
     * @param comparison Comparison operation
     */
    public ValueExpression(Comparison comparison) {
        super(comparison);
    }

    /**
     * Constructor
     * @param name Field name
     * @param value Value to match
     */
    public ValueExpression(String name, Object value) {
        this(name, EQ, value);
    }

    /**
     * Constructor
     * @param name Field name
     * @param comparison Comparison operation
     * @param value Value to match
     */
    public ValueExpression(String name, Comparison comparison, Object value) {
        super(name, comparison);
        this.value = value;
    }

    /**
     * Gets the value to match
     * @return Value to match
     */
    public Object getValue() {
        return value;
    }

    /**
     * Sets the value to match
     * @param value Value to match
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * Gets if the value should be treated as a phrase (not tokenized)
     * @return TRUE if the value should be treated as a phrase (not tokenized)
     */
    public boolean isMatchPhrase() {
        return matchPhrase;
    }

    /**
     * Sets if the value should be treated as a phrase (not tokenized)
     * @param matchPhrase TRUE if the value should be treated as a phrase (not tokenized)
     */
    public void setMatchPhrase(boolean matchPhrase) {
        this.matchPhrase = matchPhrase;
    }

    @Override
    public Float getBoost() {
        return boost;
    }

    @Override
    public ValueExpression boost(Float boost) {
        this.boost = boost;
        return this;
    }

    @Override
    public Expression optimize() {
        if (value == null) {
            return null;
        } else if (value instanceof String && ((String) value).length() == 0) {
            return null;
        } else {
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ValueExpression that = (ValueExpression) o;
        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(matchPhrase, that.matchPhrase)
                .append(value, that.value)
                .append(boost, that.boost)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(value)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, new CustomJsonToStringStyle())
                .appendSuper(super.toString())
                .append("value", value)
                .append("matchPhrase", matchPhrase)
                .append("boost", boost)
                .toString();
    }
}
