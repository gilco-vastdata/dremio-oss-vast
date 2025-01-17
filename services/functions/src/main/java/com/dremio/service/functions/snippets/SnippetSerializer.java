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
package com.dremio.service.functions.snippets;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public final class SnippetSerializer extends StdSerializer<Snippet> {
  public SnippetSerializer() { this(null);}
  public SnippetSerializer(Class<Snippet> t) {
    super(t);
  }

  @Override
  public void serialize(
    Snippet value,
    JsonGenerator gen,
    SerializerProvider provider) throws IOException {
    gen.writeString(value.toString());
  }
}
