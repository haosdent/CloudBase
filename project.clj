(defproject cloud-base "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein2-eclipse "2.0.0"]]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [http-kit "2.1.10"]
                 [compojure "1.1.5"]
                 [org.apache.hbase/hbase "0.94.0"]
                 [org.apache.hadoop/hadoop-core "1.2.1"]
                 [org.apache.hadoop/hadoop-client "1.2.1"]] 
  :java-source-paths ["src/java"]
  :main cloud-base.core) 
