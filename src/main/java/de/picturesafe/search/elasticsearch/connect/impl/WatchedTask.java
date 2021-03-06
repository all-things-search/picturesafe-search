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

package de.picturesafe.search.elasticsearch.connect.impl;

import de.picturesafe.search.util.logging.StopWatchPrettyPrint;

import org.slf4j.Logger;
import org.springframework.util.StopWatch;

public abstract class WatchedTask<T> {
    private final T result;

    public WatchedTask(Logger logger, String name) {
        final StopWatch sw = new StopWatch(name);
        sw.start();
        result = process();
        sw.stop();
        logger.debug("{}", new StopWatchPrettyPrint(sw));
    }

    public abstract T process();

    public final T getResult() {
        return result;
    }
}
