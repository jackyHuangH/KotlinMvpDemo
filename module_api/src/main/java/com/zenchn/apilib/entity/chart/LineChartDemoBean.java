package com.zenchn.apilib.entity.chart;

import java.util.List;

/**
 * @author hzj
 * @date 2018/10/16
 * description:演示用 折线图 监测分析数据
 */
public class LineChartDemoBean {
    private Boolean success;
    private Integer statusCode;
    private String message;

    private List<BuildingAnalysisBean> data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<BuildingAnalysisBean> getData() {
        return data;
    }

    public void setData(List<BuildingAnalysisBean> data) {
        this.data = data;
    }

    public static class BuildingAnalysisBean {

        /**
         * buildingId : 0
         * buildingName : 浙江省台州市椒江区海门街道中山西路与银河西巷交汇处东北50米银河商城
         * monitorPoints : [{"pointName":"裂缝一","prewarningValue":0.6,"dailyData":[{"name":"裂缝一","list":[{"value":-0.15,"date":"20181010"},{"value":-0.17,"date":"20181011"},{"value":-0.17,"date":"20181012"},{"value":-0.17,"date":"20181013"},{"value":-0.17,"date":"20181014"},{"value":-0.17,"date":"20181015"}]}]},{"pointName":"裂缝二","prewarningValue":0.6,"dailyData":[{"name":"裂缝二","list":[{"value":5.21,"date":"20181010"},{"value":5.2,"date":"20181011"},{"value":5.18,"date":"20181012"},{"value":5.19,"date":"20181013"},{"value":5.2,"date":"20181014"},{"value":5.21,"date":"20181015"}]}]},{"pointName":"倾斜一- 倾斜X","prewarningValue":12,"dailyData":[{"name":"倾斜一- 倾斜X","list":[{"value":0.52,"date":"20181010"},{"value":0.61,"date":"20181011"},{"value":0.65,"date":"20181012"},{"value":0.6,"date":"20181013"},{"value":0.58,"date":"20181014"},{"value":0.57,"date":"20181015"}]}]},{"pointName":"倾斜一- 倾斜Y","prewarningValue":12,"dailyData":[{"name":"倾斜一- 倾斜Y","list":[{"value":0.18,"date":"20181010"},{"value":0.02,"date":"20181011"},{"value":-0.02,"date":"20181012"},{"value":0.06,"date":"20181013"},{"value":0.06,"date":"20181014"},{"value":0.02,"date":"20181015"}]}]},{"pointName":"倾斜二- 倾斜X","prewarningValue":12,"dailyData":[{"name":"倾斜二- 倾斜X","list":[{"value":-0.22,"date":"20181010"},{"value":-0.15,"date":"20181011"},{"value":-0.13,"date":"20181012"},{"value":-0.17,"date":"20181013"},{"value":-0.17,"date":"20181014"},{"value":-0.13,"date":"20181015"}]}]},{"pointName":"倾斜二- 倾斜Y","prewarningValue":12,"dailyData":[{"name":"倾斜二- 倾斜Y","list":[{"value":-0.02,"date":"20181010"},{"value":-0.09,"date":"20181011"},{"value":-0.05,"date":"20181012"},{"value":-0.05,"date":"20181013"},{"value":-0.06,"date":"20181014"},{"value":-0.1,"date":"20181015"}]}]}]
         */

        private String buildingId;
        private String buildingName;
        private List<MonitorPointsBean> monitorPoints;

        public String getBuildingId() {
            return buildingId;
        }

        public void setBuildingId(String buildingId) {
            this.buildingId = buildingId;
        }

        public String getBuildingName() {
            return buildingName;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }

        public List<MonitorPointsBean> getMonitorPoints() {
            return monitorPoints;
        }

        public void setMonitorPoints(List<MonitorPointsBean> monitorPoints) {
            this.monitorPoints = monitorPoints;
        }

        public static class MonitorPointsBean {
            /**
             * pointName : 裂缝一
             * prewarningValue : 0.6
             * dailyData : [{"name":"裂缝一","list":[{"value":-0.15,"date":"20181010"},{"value":-0.17,"date":"20181011"},{"value":-0.17,"date":"20181012"},{"value":-0.17,"date":"20181013"},{"value":-0.17,"date":"20181014"},{"value":-0.17,"date":"20181015"}]}]
             */

            private String pointName;
            private Float prewarningValue;
            private List<DailyDataBean> dailyData;

            public String getPointName() {
                return pointName;
            }

            public void setPointName(String pointName) {
                this.pointName = pointName;
            }

            public Float getPrewarningValue() {
                return prewarningValue;
            }

            public void setPrewarningValue(Float prewarningValue) {
                this.prewarningValue = prewarningValue;
            }

            public List<DailyDataBean> getDailyData() {
                return dailyData;
            }

            public void setDailyData(List<DailyDataBean> dailyData) {
                this.dailyData = dailyData;
            }

            public static class DailyDataBean {
                /**
                 * name : 裂缝一
                 * list : [{"value":-0.15,"date":"20181010"},{"value":-0.17,"date":"20181011"},{"value":-0.17,"date":"20181012"},{"value":-0.17,"date":"20181013"},{"value":-0.17,"date":"20181014"},{"value":-0.17,"date":"20181015"}]
                 */

                private String name;
                private List<ListBean> list;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public List<ListBean> getList() {
                    return list;
                }

                public void setList(List<ListBean> list) {
                    this.list = list;
                }

                public static class ListBean {
                    /**
                     * value : -0.15
                     * date : 20181010
                     */

                    private Float value;
                    private String date;

                    public Float getValue() {
                        return value;
                    }

                    public void setValue(Float value) {
                        this.value = value;
                    }

                    public String getDate() {
                        return date;
                    }

                    public void setDate(String date) {
                        this.date = date;
                    }

                    @Override
                    public String toString() {
                        return "ListBean{" +
                                "value=" + value +
                                ", date='" + date + '\'' +
                                '}';
                    }
                }

                @Override
                public String toString() {
                    return "DailyDataBean{" +
                            "name='" + name + '\'' +
                            ", list=" + list +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "MonitorPointsBean{" +
                        "pointName='" + pointName + '\'' +
                        ", prewarningValue=" + prewarningValue +
                        ", dailyData=" + dailyData +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "BuildingAnalysisBean{" +
                    "buildingId='" + buildingId + '\'' +
                    ", buildingName='" + buildingName + '\'' +
                    ", monitorPoints=" + monitorPoints +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "LineChartDemoBean{" +
                "success=" + success +
                ", statusCode=" + statusCode +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
