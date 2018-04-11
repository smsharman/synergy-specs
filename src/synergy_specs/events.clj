(ns synergy-specs.events
  (:require [clojure.spec.alpha :as s]
            [clj-uuid :as uuid]))


(s/def ::eventID string?)
(s/def ::eventAction string?)
(s/def ::eventData (s/map-of keyword? string?))
(s/def ::eventVisible string?)
(s/def ::eventStatus boolean?)

(s/def ::userID string?)
(s/def ::eventParent string?)
(s/def ::originEvent string?)
(s/def ::eventTimestamp #(instance? Long %))

(s/def ::inboundEvent (s/keys :req [
                                    ::eventID
                                    ::eventAction
                                    ::eventData
                                    ]))

(s/def ::synergyEvent (s/keys :req [
                                    ::eventID
                                    ::userID
                                    ::eventParent
                                    ::originEvent
                                    ::eventAction
                                    ::eventData
                                    ::eventTimestamp
                                    ::eventStatus
                                    ::eventVisible
                                    ]))
