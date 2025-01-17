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
import Immutable from "immutable";

export function getProvision(state, id) {
  return state.resources.entities.getIn(["provision", id]) || Immutable.Map();
}

export function getAllProvisions(state) {
  const provisions =
    state.resources.entities.get("provision") || Immutable.Map();
  return provisions
    .toList()
    .sortBy((provision) => provision.getIn(["id", "id"])) // ultimate fallback stability
    .sortBy((provision) => provision.get("name"))
    .sortBy((provision) => provision.get("clusterType"));
}

export function getClouds(state) {
  return state.resources.entities.get("clouds");
}

export function getAwsDefaults(state) {
  return state.resources.entities.get("awsDefaults");
}

export const getEngineDefaults = (state) => {
  return state.resources.entities.get("engineDefaults");
};
