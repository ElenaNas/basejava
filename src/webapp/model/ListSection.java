package webapp.model;

import java.util.List;
import java.util.Objects;

public class ListSection {

    private final List<String>dataList;

    public ListSection(List<String> dataList) {
        this.dataList = dataList;
    }

    public List<String> getDataList() {
        return dataList;
    }

    @Override
    public String toString() {
        return "ListSection{" +
                "dataList=" + dataList +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof
                ListSection that)) return false;
        return Objects.equals(getDataList(), that.getDataList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDataList());
    }
}
