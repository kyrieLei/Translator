package com.example.translator_4_17.api


import com.example.translator_4_17.db.data.Definition
import com.example.translator_4_17.db.data.Language
import com.example.translator_4_17.db.data.Translation
import com.example.translator_4_17.util.Retrofit
import com.example.translator_4_17.util.TranslationEngine

class Engine : TranslationEngine(
    name = "Lingva",
    defaultUrl = "https://lingva.ml",
    autoLanguageCode = "auto"
) {

    private lateinit var api: Translate
    override fun create(): TranslationEngine = apply {
        api = Retrofit.createApi(this)
    }

    override suspend fun getLanguages() = listOf(
        Language(code = "ar", name = "阿拉伯语"),
        Language(code = "bg", name = "保加利亚语"),
        Language(code = "zh", name = "简体中文"),
        Language(code = "zh_HANT", name = "繁体中文"),
        Language(code = "cs", name = "捷克语"),
        Language(code = "nl", name = "荷兰语"),
        Language(code = "en", name = "英语"),
        Language(code = "et", name = "爱沙尼亚语"),
        Language(code = "fi", name = "芬兰语"),
        Language(code = "fr", name = "法语"),
        Language(code = "ka", name = "格鲁吉亚语"),
        Language(code = "de", name = "德语"),
        Language(code = "el", name = "希腊语"),
        Language(code = "hu", name = "匈牙利语"),
        Language(code = "id", name = "印度尼西亚语"),
        Language(code = "ga", name = "爱尔兰语"),
        Language(code = "it", name = "意大利语"),
        Language(code = "ja", name = "日语"),
        Language(code = "ko", name = "汉语"),
        Language(code = "la", name = "拉丁语"),
        Language(code = "ms", name = "马来语"),
        Language(code = "mn", name = "蒙古语"),
        Language(code = "pl", name = "波兰语"),
        Language(code = "pt", name = "葡萄牙语"),
        Language(code = "ro", name = "罗马尼亚语"),
        Language(code = "ru", name = "俄语"),
        Language(code = "sl", name = "斯洛文尼亚语"),
        Language(code = "es", name = "西班牙语"),
        Language(code = "th", name = "泰语")
    )

    override suspend fun translate(query: String, source: String, target: String): Translation {
        val response = api.translate(
            sourceOrAuto(source),
            target,
            query.replace("/", "")
        )
        return Translation(
            translatedText = response.translation,
            detectedLanguage = response.info?.detectedSource,
            examples = response.info?.examples,
            definitions = response.info?.definitions
                ?.map {
                    Definition(
                        type = it.type,
                        definition = it.list.first().definition,
                        example = it.list.first().example,
                    )
                }
        )
    }

}
