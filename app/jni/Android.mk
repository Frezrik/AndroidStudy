# Copyright (C) 2009 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

LOCAL_MODULE    := jniapi

MY_CPP_LIST := $(wildcard $(LOCAL_PATH)/cppstudy/*.cpp)
LOCAL_SRC_FILES := $(MY_CPP_LIST:$(LOCAL_PATH)/%=%)

$(warning $(LOCAL_SRC_FILES))

LOCAL_LDLIBS :=-llog

include $(BUILD_SHARED_LIBRARY)

#-----------------------------------------------------------

include $(CLEAR_VARS)

LOCAL_MODULE    := jnistudy
LOCAL_SRC_FILES := cstudy/test.c

LOCAL_LDLIBS :=-llog

include $(BUILD_SHARED_LIBRARY)