package domainapp.modules.exampledom.module.classification.dom.menu;

import org.apache.isis.applib.annotation.*;
import org.incode.module.classification.dom.impl.category.Category;
import org.incode.module.classification.dom.impl.category.CategoryRepository;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY
)
@DomainServiceLayout(
        named = "Taxonomies",
        menuOrder = "10"
)
public class TaxonomyMenu {


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT)
    @MemberOrder(sequence = "1")
    public List<Category> listAll() {
        return categoryRepository.findByParent(null);
    }


    @javax.inject.Inject
    CategoryRepository categoryRepository;

}
