package sz.lzh.conversation.dao.cache;

import java.util.List;

import org.springframework.stereotype.Service;


@Service
public abstract class CacheInfo<T> {

	
		public abstract void init();
		public abstract void update(List<T> list);
		public abstract void update(T t);
		public abstract void remove(List<T> list);
		public abstract void remove(T t);
		protected abstract void setData(T t);
		protected abstract void clean();
}
