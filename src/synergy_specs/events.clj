(ns synergy-specs.events
  (:require [clojure.spec.alpha :as s]
            [clj-uuid :as uuid]))

(defn check-string-uuid? [input-uuid]
  "Check whether a given string is in UUID standard format or not"
  (try
     (uuid? (java.util.UUID/fromString input-uuid))
     (catch IllegalArgumentException e
       false)))

;; Synergy Event defintions
(s/def ::eventId (s/and string? #(check-string-uuid? %)))
(s/def ::parentId (s/and string? #(check-string-uuid? %)))
(s/def ::originId string?)
(s/def ::userId string?)
(s/def ::orgId string?)
(s/def ::eventVersion integer?)
(s/def ::eventAction string?)
(s/def ::eventData map?)
(s/def ::eventTimestamp string?)

(s/def ::synergyEvent (s/keys :req [
                                    ::eventId
                                    ::parentId
                                    ::originId
                                    ::userId
                                    ::orgId
                                    ::eventVersion
                                    ::eventAction
                                    ::eventData
                                    ::eventTimestamp
                                    ]))

(def testMessage1 {
                   :synergy-specs.events/eventId "7a5a815b-2e52-4d40-bec8-c3fc06edeb36"
                   :synergy-specs.events/parentId "7a5a815b-2e52-4d40-bec8-c3fc06edeb36"
                   :synergy-specs.events/originId "7a5a815b-2e52-4d40-bec8-c3fc06edeb36"
                   :synergy-specs.events/userId "1"
                   :synergy-specs.events/orgId "1"
                   :synergy-specs.events/eventVersion 1
                   :synergy-specs.events/eventAction "event1"
                   :synergy-specs.events/eventData {
                               :key1 "value1"
                               :key2 "value2"
                               }
                   :synergy-specs.events/eventTimestamp "2018-10-09T12:24:03.390+0000"
                   })

(def testMessage2 {
                   :eventId "7a5a815b-2e52-4d40-bec8-c3fc06edeb36"
                   :parentId "7a5a815b-2e52-4d40-bec8-c3fc06edeb36"
                   :originId "7a5a815b-2e52-4d40-bec8-c3fc06edeb36"
                   :userId "1"
                   :orgId "1"
                   :eventVersion 1
                   :eventAction "event1"
                   :eventData {
                                                    :key1 "value1"
                                                    :key2 "value2"
                                                    }
                   :eventTimestamp "2018-10-09T12:24:03.390+0000"
                   })

(defn wrap-std-event [event]
  "Cast transport message into namespaced message"
  {
   :synergy-specs.events/eventId        (get event :eventId)
   :synergy-specs.events/parentId       (get event :parentId)
   :synergy-specs.events/originId       (get event :originId)
   :synergy-specs.events/userId         (get event :userId)
   :synergy-specs.events/orgId          (get event :orgId)
   :synergy-specs.events/eventVersion   (get event :eventVersion)
   :synergy-specs.events/eventAction    (get event :eventAction)
   :synergy-specs.events/eventData      (get event :eventData)
   :synergy-specs.events/eventTimestamp (get event :eventTimestamp)
   })

(defn unwrap-std-event [event]
  "Cast transport message into namespaced message"
  {
   :eventId        (get event :synergy-specs.events/eventId)
   :parentId       (get event :synergy-specs.events/parentId)
   :originId       (get event :synergy-specs.events/originId)
   :userId         (get event :synergy-specs.events/userId)
   :orgId          (get event :synergy-specs.events/orgId)
   :eventVersion   (get event :synergy-specs.events/eventVersion)
   :eventAction    (get event :synergy-specs.events/eventAction)
   :eventData      (get event :synergy-specs.events/eventData)
   :eventTimestamp (get event :synergy-specs.events/eventTimestamp)
   })

(defn generate-new-eventId []
  (.toString (java.util.UUID/randomUUID)))