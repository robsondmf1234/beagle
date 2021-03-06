/*
 * Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
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

import XCTest
@testable import BeagleSchema
import SnapshotTesting

class PageViewTests: XCTestCase {

    func test_whenDecodingJson_thenItShouldReturnAPageView() throws {
        let component: PageView = try componentFromJsonFile(fileName: "PageViewWith3Pages")
        _assertInlineSnapshot(matching: component, as: .dump, with: """
        ▿ PageView
          ▿ children: 3 elements
            ▿ UnknownComponent
              - type: "custom:beagleschematestscomponent"
            ▿ UnknownComponent
              - type: "custom:beagleschematestscomponent"
            ▿ UnknownComponent
              - type: "custom:beagleschematestscomponent"
          - context: Optional<Context>.none
          - pageIndicator: Optional<PageIndicatorComponent>.none
        """)
    }

    func test_whenDecodingJson_thenItShouldReturnPageViewWithIndicator() throws {
        let component: PageView = try componentFromJsonFile(fileName: "PageViewWith3PagesAndIndicator")
        _assertInlineSnapshot(matching: component, as: .dump, with: """
        ▿ PageView
          ▿ children: 3 elements
            ▿ UnknownComponent
              - type: "custom:beagleschematestscomponent"
            ▿ UnknownComponent
              - type: "custom:beagleschematestscomponent"
            ▿ UnknownComponent
              - type: "custom:beagleschematestscomponent"
          - context: Optional<Context>.none
          ▿ pageIndicator: Optional<PageIndicatorComponent>
            ▿ some: PageIndicator
              - selectedColor: Optional<String>.none
              - unselectedColor: Optional<String>.none
        """)
    }

}
