(defproject cloud-base "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein2-eclipse "2.0.0"]]
  :repositories [["cloudera" "https://repository.cloudera.com/artifactory/cloudera-repos/"]
                 ["alihbase" "http://mvnrepo.taobao.ali.com:8081/nexus/content/repositories/thirdparty/"]
                 ]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [http-kit "2.1.10"]
                 [cheshire "5.2.0"]
                 [org.apache.hbase/hbase "0.94-adh3u3"]
                 [org.apache.hadoop/hadoop-core "2.0.0-mr1-cdh4.3.1"]
                 [org.apache.hadoop/hadoop-client "2.0.0-cdh4.3.1"]]
  :java-source-paths ["src/java"]
  :main cloud-base.core)
