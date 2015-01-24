
// message has custom fields exposed as properties
// standard GELF fields are properties directly available on the message object
// additional fields are available via getField()
// you can also add fields or modify the contents of existing fields

print(message + "\r\n");
print(message.message + "\r\n");
print(message.source + "\r\n");
print(message.getField("guid") + "\r\n");

/**
 *
 *
 * Copyright 2015 Ronald Rink, d-fens GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
