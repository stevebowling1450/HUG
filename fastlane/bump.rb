#!/usr/bin/env ruby

require 'httparty'
require 'json'

$app_id = "4df09011faa04ca3b7cdc8e7808f247a"
$hockeyapp_url = "https://rink.hockeyapp.net/api/2/apps/#{$app_id}/app_versions"
$hockeyapp_api_token = "c71c2639f55e40ccbc49c68c8a1cb303"

def get_version_code
  response = HTTParty.get($hockeyapp_url, :headers => { "X-HockeyAppToken" => $hockeyapp_api_token })
  json = JSON.parse(response.body)
  app_versions = json["app_versions"]
  $version_code = app_versions[0]["version"].to_i
end

def get_version_name
  response = HTTParty.get($hockeyapp_url, :headers => { "X-HockeyAppToken" => $hockeyapp_api_token })
  json = JSON.parse(response.body)
  app_versions = json["app_versions"]
  $version_name = app_versions[0]["shortversion"]
end

def set_version_code
  $version_code += 1
  file_str = File.read("../app/build.gradle")
  new_contents = file_str.gsub(/versionCode [0-9]+/, "versionCode #{$version_code}")
  File.open("../app/build.gradle", "w") {|file| file.puts new_contents}
end

def set_version_name
  file_str = File.read("../app/build.gradle")
  new_contents = file_str.gsub(/versionName "[0-9]+.[0-9]+(.[0-9])*"/, "versionName \"#{$version_name}\"")
  File.open("../app/build.gradle", "w") {|file| file.puts new_contents}
end

get_version_code
get_version_name

set_version_code
set_version_name