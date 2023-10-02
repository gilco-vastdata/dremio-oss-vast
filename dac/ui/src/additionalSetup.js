/*
 * Copyright (C) 2017-2019 Dremio Corporation
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
import { getLoggingContext } from "dremio-ui-common/contexts/LoggingContext.js";
import { consoleLogger } from "dremio-ui-common/utilities/consoleLogger.js";
import "./contexts/SonarContext";
import "./contexts/TracingContext";
import { setupLang } from "./setupLang";
import "./contexts/ApiContext";
import "./contexts/SessionContext";

export const additionalSetup = async () => {
  await setupLang();
  getLoggingContext().registerHandler(consoleLogger);
};