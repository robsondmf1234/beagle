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

package br.com.zup.beagle.sample.builder

import br.com.zup.beagle.core.CornerRadius
import br.com.zup.beagle.core.Style
import br.com.zup.beagle.ext.applyStyle
import br.com.zup.beagle.ext.unitPercent
import br.com.zup.beagle.ext.unitReal
import br.com.zup.beagle.widget.action.*
import br.com.zup.beagle.widget.context.ContextData
import br.com.zup.beagle.widget.core.*
import br.com.zup.beagle.widget.layout.Container
import br.com.zup.beagle.widget.layout.NavigationBar
import br.com.zup.beagle.widget.layout.Screen
import br.com.zup.beagle.widget.layout.ScreenBuilder
import br.com.zup.beagle.widget.ui.Button
import br.com.zup.beagle.widget.ui.Text
import br.com.zup.beagle.widget.ui.TextInput

class Address(val data: Data) {}

class Data(
    var zip: String,
    var street: String,
    var number: String,
    var neighborhood: String,
    var city: String,
    var state: String,
    var complement: String
) {}

object ScreenContextBuilder : ScreenBuilder {
    var styleMargim =  Style(
        size = Size(height = 30.unitReal()),
        margin = EdgeValue(
            top = 15.unitReal(),
            left = 25.unitReal(),
            right = 25.unitReal()
        )
    )
    override fun build() = Screen(
        navigationBar = NavigationBar(
            title = "Beagle Context",
            showBackButton = true
        ),
        child = Container(
            listOf(
                Text(
                    text = "Preencha o Formulário",
                    styleId = "DesignSystem.Text.helloWord"
                ).applyStyle(
                    Style(
                        margin = EdgeValue(top = 30.unitReal(),bottom = 30.unitReal()),
                        flex = Flex(
                            alignSelf = AlignSelf.CENTER
                        )
                    )
                ),
                TextInput(
                    placeholder = "CEP",
                    value = "@{address.data.zip}",
                    styleId = "DesignSystem.TextInput.Style.Bff",
                    onChange = listOf(
                        SetContext(
                            contextId = "address",
                            path = "data.zip",
                            value = "@{onChange.value}"
                        )
                    ),
                    onBlur = listOf(
                        SendRequest(
                            url = "https://viacep.com.br/ws/@{onBlur.value}/json",
                            method = RequestActionMethod.GET,
                            onSuccess = listOf(
                                SetContext(
                                    contextId = "address",
                                    path = "data",
                                    value =
                                    Data(
                                        zip = "@{onBlur.value}",
                                        street = "@{onSuccess.data.logradouro}",
                                        number = "@{address.data.number}",
                                        neighborhood = "@{onSuccess.data.bairro}",
                                        city = "@{onSuccess.data.localidade}",
                                        state = "@{onSuccess.data.uf}",
                                        complement = "@{address.data.complement}"
                                    )
                                )
                            )
                        )
                    )
                ).applyStyle(styleMargim),

                createTextInput(
                    textInputPlaceholder = "Rua",
                    textInputValue = "@{address.data.street}",
                    contextPath = "data.street"
                ),

                createTextInput(
                    textInputPlaceholder = "Número",
                    textInputValue = "@{address.data.number}",
                    contextPath = "data.number",
                    type = TextInputType.NUMBER
                ),

                createTextInput(
                    textInputPlaceholder = "Bairro",
                    textInputValue = "@{address.data.neighborhood}",
                    contextPath = "data.neighborhood"
                ),

                createTextInput(
                    textInputPlaceholder = "Cidade",
                    textInputValue = "@{address.data.city}",
                    contextPath = "data.city"
                ),

                createTextInput(
                    textInputPlaceholder = "Estado",
                    textInputValue = "@{address.data.state}",
                    contextPath = "data.state"
                ),

                createTextInput(
                    textInputPlaceholder = "Complemento",
                    textInputValue = "@{address.data.complement}",
                    contextPath = "data.complement"
                ),

                Button(
                    text = "Enviar",
                    styleId = "DesignSystem.Button.Context",
                    onPress = listOf(
                        Confirm(
                            title = "Formulário de endereço!",
                            message = "Os dados estão corretos?\n" +
                                "Rua: @{address.data.street}\n" +
                                 "Número: @{address.data.number}\n" +
                                "Bairro: @{address.data.neighborhood}\n" +
                                "Cidade: @{address.data.city}\n" +
                                "Estado: @{address.data.state}\n" +
                                "Complemento: @{address.data.complement}",
                            onPressOk = Alert(
                                title = "Formulário de endereço",
                                message = "O formulário foi enviado com sucesso!",
                                onPressOk = SetContext(
                                    contextId = "address",
                                    path = "data",
                                    value =
                                    Data(
                                        zip = "",
                                        street = "",
                                        number = "",
                                        neighborhood = "",
                                        city = "",
                                        state = "",
                                        complement = ""
                                    )
                                )
                            )
                        )
                    )
                ).applyStyle(
                    Style(
                        backgroundColor = "#808080",
                        cornerRadius = CornerRadius(8.0),
                        size = Size(width = 50.unitPercent(), height = 30.unitReal()),
                        margin = EdgeValue(
                            top = 50.unitReal()
                        ),
                        flex = Flex(
                            alignSelf = AlignSelf.CENTER
                        )
                    )
                )

            ),
            context = ContextData(
                id = "address",
                value = Address(data = Data(
                    zip = "",
                    street = "",
                    number = "",
                    neighborhood = "",
                    city = "",
                    state = "",
                    complement = ""
                )
                )
            )
        )
    )

    private fun createTextInput(
        textInputPlaceholder: String,
        textInputValue: String,
        contextPath: String,
        type: TextInputType? = null
    ): TextInput = TextInput(
        placeholder = textInputPlaceholder,
        value = textInputValue,
        type = type,
        styleId = "DesignSystem.TextInput.Style.Bff",
        onChange = listOf(
            SetContext(
                contextId = "address",
                path = contextPath,
                value = "@{onChange.value}"
            )
        )
    ).applyStyle(styleMargim)
}