package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new rocks.poopjournal.todont.DataBinderMapperImpl());
  }
}
