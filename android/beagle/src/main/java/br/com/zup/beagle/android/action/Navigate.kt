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

package br.com.zup.beagle.android.action

import br.com.zup.beagle.android.components.layout.Screen
import br.com.zup.beagle.android.view.custom.BeagleNavigator
import br.com.zup.beagle.android.widget.RootView

sealed class Navigate : Action{

    data class OpenExternalURL(val url: String) : Navigate() {
        override fun execute(rootView: RootView) {
            BeagleNavigator.openExternalURL(rootView.getContext(), url)
        }
    }

    class OpenNativeRoute(val route: String,
                          val shouldResetApplication: Boolean = false,
                          val data: Map<String, String>? = null) : Navigate() {
        override fun execute(rootView: RootView) {
            BeagleNavigator.openNativeRoute(rootView.getContext(), route, data, shouldResetApplication)
        }
    }

    data class PushStack(val route: Route) : Navigate() {
        override fun execute(rootView: RootView) {
            BeagleNavigator.pushStack(rootView.getContext(), route)
        }
    }

    class PopStack : Navigate() {
        override fun execute(rootView: RootView) {
            BeagleNavigator.popStack(rootView.getContext())
        }
    }

    data class PushView(val route: Route) : Navigate() {
        override fun execute(rootView: RootView) {
            BeagleNavigator.pushView(rootView.getContext(), route)
        }
    }

    class PopView : Navigate() {
        override fun execute(rootView: RootView) {
            BeagleNavigator.popView(rootView.getContext())
        }
    }

    data class PopToView(val route: String) : Navigate() {
        override fun execute(rootView: RootView) {
            BeagleNavigator.popToView(rootView.getContext(), route)
        }
    }

    data class ResetApplication(val route: Route) : Navigate() {
        override fun execute(rootView: RootView) {
            BeagleNavigator.resetApplication(rootView.getContext(), route)
        }
    }

    data class ResetStack(val route: Route) : Navigate() {
        override fun execute(rootView: RootView) {
            BeagleNavigator.resetStack(rootView.getContext(), route)
        }
    }
}

sealed class Route {
    /**
     * Class that takes care of navigation to remote content.
     * @param url attribute that contains the navigation endpoint.
     * @param shouldPrefetch tells Beagle if the navigation request should be previously loaded or not.
     * @param fallback screen that is rendered in case the request fails.
     */
    data class Remote(val url: String, val shouldPrefetch: Boolean = false, val fallback: Screen? = null) : Route()

    /**
     * Class indicating navigation to a local screen.
     * @param screen screen to be rendered.
     */
    data class Local(val screen: Screen) : Route()
}
