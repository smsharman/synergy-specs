(ns synergy-specs.events-test
  (:require [clojure.test :refer :all]
            [synergy-specs.events :refer :all]))

(def valid-message {:eventId "7a5a815b-2e52-4d40-bec8-c3fc06edeb36"
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
                    :eventTimestamp "2020-04-17T11:23:10.904Z"
                    })

(def ns-valid-message {
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
                       :synergy-specs.events/eventTimestamp "2020-04-17T11:23:10.904Z"
                       })

(deftest wrap-std-event-test
  (is (= ns-valid-message (wrap-std-event valid-message))))

(deftest unwrap-std-event-test
  (is (= valid-message (unwrap-std-event ns-valid-message))))

(deftest check-string-uuid?-test
  (is (true? (check-string-uuid? (get valid-message :eventId))))
  (is (true? (check-string-uuid? (get valid-message :parentId))))
  (is (true? (check-string-uuid? (get valid-message :originId))))
  (is (false? (check-string-uuid? "1234567889"))))

(deftest check-timestamp-instant?-test
  (is (true? (check-timestamp-instant? (get valid-message :eventTimestamp))))
  (is (false? (check-timestamp-instant? "3:30am Tuesday 21st September 2021"))))

(deftest generate-new-eventId-test
  (let [generated-uuid (generate-new-eventId)]
   (is (true? (check-string-uuid? generated-uuid)))))

(deftest generate-new-timestamp-test
  (let [generated-timestamp (generate-new-timestamp)]
    (is (true? (check-timestamp-instant? generated-timestamp)))))