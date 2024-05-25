// Code generated by moshi-kotlin-codegen. Do not edit.
@file:Suppress("DEPRECATION", "unused", "ClassName", "REDUNDANT_PROJECTION",
    "RedundantExplicitType", "LocalVariableName", "RedundantVisibilityModifier",
    "PLATFORM_CLASS_MAPPED_TO_KOTLIN", "IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION")

package com.example.weather.`data`

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.Moshi
import com.squareup.moshi.`internal`.Util
import java.lang.NullPointerException
import kotlin.String
import kotlin.Suppress
import kotlin.Unit
import kotlin.collections.emptySet
import kotlin.text.buildString

public class OpenWeatherListWeatherJsonJsonAdapter(
  moshi: Moshi,
) : JsonAdapter<OpenWeatherListWeatherJson>() {
  private val options: JsonReader.Options = JsonReader.Options.of("description", "icon")

  private val stringAdapter: JsonAdapter<String> = moshi.adapter(String::class.java, emptySet(),
      "description")

  public override fun toString(): String = buildString(48) {
      append("GeneratedJsonAdapter(").append("OpenWeatherListWeatherJson").append(')') }

  public override fun fromJson(reader: JsonReader): OpenWeatherListWeatherJson {
    var description: String? = null
    var icon: String? = null
    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(options)) {
        0 -> description = stringAdapter.fromJson(reader) ?:
            throw Util.unexpectedNull("description", "description", reader)
        1 -> icon = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("icon", "icon",
            reader)
        -1 -> {
          // Unknown name, skip it.
          reader.skipName()
          reader.skipValue()
        }
      }
    }
    reader.endObject()
    return OpenWeatherListWeatherJson(
        description = description ?: throw Util.missingProperty("description", "description",
            reader),
        icon = icon ?: throw Util.missingProperty("icon", "icon", reader)
    )
  }

  public override fun toJson(writer: JsonWriter, value_: OpenWeatherListWeatherJson?): Unit {
    if (value_ == null) {
      throw NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.")
    }
    writer.beginObject()
    writer.name("description")
    stringAdapter.toJson(writer, value_.description)
    writer.name("icon")
    stringAdapter.toJson(writer, value_.icon)
    writer.endObject()
  }
}
